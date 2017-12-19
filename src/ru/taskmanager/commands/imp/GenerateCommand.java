package ru.taskmanager.commands.imp;

import ru.taskmanager.api.mappers.LocalVersionManager;
import ru.taskmanager.args.params.BooleanParam;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;

import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.ListUtils;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StringUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenerateCommand extends SafetyCommand {

    @Override
    public List<CommandResult> safetyExecute(List<KeyValueParam> params) throws CommandException {
        KeyValueParam typeParam = ListUtils.getKeyValueParam(params, "t");
        KeyValueParam fileNameParam = ListUtils.getKeyValueParam(params, "n");
        KeyValueParam useTimeStampParam  = ListUtils.getKeyValueParam(params, "stamp");
        KeyValueParam out = ListUtils.getKeyValueParam(params, "out");

        String fileExtension = "txt";
        if(null != typeParam){
            try {
                fileExtension = typeParam.getStringValue();
            } catch (StringIsEmptyException e) {}
        }

        String fileName = "file";
        if(null != fileNameParam){
            try {
                fileName = fileNameParam.getStringValue();
            } catch (StringIsEmptyException e) {}
        }

        Boolean useTimeStamp = true;
        if(null != useTimeStampParam){
            useTimeStamp = ((BooleanParam)useTimeStampParam).getBooleanValue();
        }

        String timeStamp = "";
        if(useTimeStamp){
            Date date = new Date();
            timeStamp = StringUtils.sdf.format(date) + "_";
        }

        String formatFileName = String.format("%1$s%2$s.%3$s", timeStamp, fileName, fileExtension);
        LocalVersionManager manager = new LocalVersionManager();
        if(null != out){
            String outValue = null;
            try {
                outValue = out.getStringValue();
            } catch (StringIsEmptyException e) {}

            if(!StringUtils.isNullOrEmpty(outValue)){
                manager.setBaseDir(outValue);
            }
        }
        Path file = manager.createFile(formatFileName);

        List<CommandResult> result = new ArrayList<>(1);
        SuccessResult successResult = new SuccessResult();
        successResult.setMessage(String.format("File '%1$s' created", file));
        successResult.addMetaData("file", file);

        result.add(successResult);
        return result;
    }
}
