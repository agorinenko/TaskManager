package ru.taskmanager.commands.imp;

import ru.taskmanager.api.*;
import ru.taskmanager.api.mappers.LocalVersionManager;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.ErrorResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.PowerShellException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.output.ConsolePrinter;
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
    public List<CommandResult> safetyExecute(List<KeyValueParam> params) throws CommandException {

        HashMap<String, Object> psParams = ListUtils.convertToHashMap(params);
        LocalVersionManager manager = new LocalVersionManager(params);

        String v = getStringParam(params, "v");
        boolean versionIsMissing = StringUtils.isNullOrEmpty(v) ? true : false;

        List<CommandResult> result = new ArrayList<>();
        try {
            List<Version> localVersions = manager.select(".ps1");
            localVersions.sort(new VersionComparator());
            for (int i = 0; i < localVersions.size(); i++) {
                LocalVersion localVersion = (LocalVersion) localVersions.get(i);
                if(versionIsMissing || localVersion.getVersionTimestampString().equalsIgnoreCase(v)) {
                    CommandResult scriptResult = executeOperation(localVersion, psParams);
                    result.add(scriptResult);
                }
            }
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }

        return result;
    }

    private CommandResult executeOperation(LocalVersion localVersion, HashMap<String, Object> psParams) throws CommandException {
        Path path = localVersion.getLocalPath();
        try {
            PowerShellResult shellResult = CommonUtils.executePowerShellScript(path, psParams);
            if(shellResult.getErrorLines() > 0) {
                return new ErrorResult(new CommandException(String.format("The script %s was completed with errors", path)));
            } else {
                SuccessResult successResult = new SuccessResult();
                successResult.setMessage(String.format("The script %s was successful", path));
                return successResult;
            }
        } catch (PowerShellException e) {
            throw new CommandException(e.getMessage());
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
