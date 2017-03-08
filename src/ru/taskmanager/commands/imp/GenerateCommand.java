package ru.taskmanager.commands.imp;

import ru.taskmanager.args.params.BooleanParam;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.args.params.StringParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;

import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.ListUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class GenerateCommand extends SafetyCommand {
    private static final DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();
        String resultMessage = "";

        KeyValueParam typeParam = ListUtils.getKeyValueParam(params, "t");
        KeyValueParam fileNameParam = ListUtils.getKeyValueParam(params, "n");
        KeyValueParam useTimeStampParam  = ListUtils.getKeyValueParam(params, "stamp");

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
            timeStamp = sdf.format(date) + "_";
        }

        String formatFileName = String.format("%1$s%2$s.%3$s", timeStamp, fileName, fileExtension);

        createFile(formatFileName);

        resultMessage = String.format("File '%1$s' created", formatFileName);
        result.setMessage(resultMessage);
        return result;
    }

    private void createFile(String path) throws CommandException {
        File f = new File(path);

        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
        } catch (IOException e) {
            throw new CommandException("Create new file '" + path + "' ioexception");
        }
    }
}
