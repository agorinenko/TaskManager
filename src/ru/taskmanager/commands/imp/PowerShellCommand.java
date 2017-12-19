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
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {

        HashMap<String, Object> psParams = ListUtils.convertToHashMap(params);
        LocalVersionManager manager = new LocalVersionManager();

        String outValue = getStringParam(params, "out");
        if(!StringUtils.isNullOrEmpty(outValue)){
            manager.setBaseDir(outValue);
        }

        String v = getStringParam(params, "v");
        boolean versionIsMissing = StringUtils.isNullOrEmpty(v) ? true : false;

        boolean isError = false;
        try {
            List<Version> localVersions = manager.select(".ps1");
            localVersions.sort(new VersionComparator());
            for (int i = 0; i < localVersions.size(); i++) {
                LocalVersion localVersion = (LocalVersion) localVersions.get(i);
                if(versionIsMissing || localVersion.getVersionTimestampString().equalsIgnoreCase(v)) {
                    PowerShellResult result = executeOperation(localVersion, psParams);
                    if(result.getErrorLines() > 0){
                        isError = true;
                    }
                }
            }
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }

        CommandResult result;
        String message;
        if(isError){
            result = new ErrorResult(new CommandException("The power shell command was successful with errors"));
            message = "The power shell command was successful with errors" + System.lineSeparator();
        } else{
            result = new SuccessResult();
            message = "The power shell command was successful" + System.lineSeparator();
        }
        result.setMessage(message);
        return result;
    }

    private PowerShellResult executeOperation(LocalVersion localVersion, HashMap<String, Object> psParams) throws CommandException {
        Path path = localVersion.getLocalPath();
        try {
            return CommonUtils.executePowerShellScript(path, psParams);
        } catch (PowerShellException e) {
            throw new CommandException(e.getMessage());
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
