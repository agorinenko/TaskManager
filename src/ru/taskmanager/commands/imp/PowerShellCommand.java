package ru.taskmanager.commands.imp;

import ru.taskmanager.api.LocalVersion;
import ru.taskmanager.api.Version;
import ru.taskmanager.api.VersionComparator;
import ru.taskmanager.api.VersionsRepository;
import ru.taskmanager.api.mappers.LocalVersionManager;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.PowerShellException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.CommonUtils;
import ru.taskmanager.utils.ListUtils;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StringUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by agorinenko on 28.09.2017.
 */
public class PowerShellCommand extends SafetyCommand {

    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();

        HashMap<String, Object> psParams = ListUtils.convertToHashMap(params);
        LocalVersionManager manager = new LocalVersionManager();

        KeyValueParam out = ListUtils.getKeyValueParam(params, "out");
        if(null != out){
            String outValue;
            try {
                outValue = out.getStringValue();
                if(!StringUtils.isNullOrEmpty(outValue)){
                    manager.setBaseDir(outValue);
                }
            } catch (StringIsEmptyException e) {}
        }

        KeyValueParam versionParam = ListUtils.getKeyValueParam(params, "v");
        String v = null != versionParam ? versionParam.getDefaultOrStringValue("") : "";
        boolean versionIsMissing = StringUtils.isNullOrEmpty(v) ? true : false;

        StringBuilder sb = new StringBuilder();
        try {
            List<Version> localVersions = manager.select(".ps1");
            localVersions.sort(new VersionComparator());
            for (int i = 0; i < localVersions.size(); i++) {
                String psResult = "";
                LocalVersion localVersion = (LocalVersion) localVersions.get(i);

                Path path = localVersion.getLocalPath();

                if(versionIsMissing || localVersion.getVersionTimestampString().equalsIgnoreCase(v)) {
                    try {
                        CommonUtils.executePowerShellScript(path, psParams);
                    } catch (PowerShellException e) {
                        psResult = e.getMessage();
                    }
                } else {
                    psResult = "MISSED";
                }

                sb.append(psResult);
                StringUtils.appendLineSeparator(sb);
            }
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }

        String resultMessage = sb.toString();
        result.setMessage(resultMessage);
        return result;
    }
}
