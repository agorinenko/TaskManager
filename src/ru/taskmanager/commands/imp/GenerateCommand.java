package ru.taskmanager.commands.imp;

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
import java.util.Date;
import java.util.List;

public class GenerateCommand extends SafetyCommand {
    private static final DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");

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
        Path file = createFile(formatFileName);

        resultMessage = String.format("File '%1$s' created", file);
        result.setMessage(resultMessage);
        return result;
    }

    private Path createFile(String filePath) throws CommandException {
        String baseDir = SettingsUtils.getSettingsValue("out");

        if(!StringUtils.isNullOrEmpty(baseDir)){
            filePath = String.format("%1$s\\%2$s", StringUtils.trimEnd(baseDir, "\\\\"), StringUtils.trimStart(filePath, "\\\\"));
        }

        Path path = Paths.get(filePath);
        path = path.normalize();

        Path parent  = path.getParent();
        try {

            if(null != parent && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            throw new CommandException(String.format("Create new dir '%1$s' IOException", parent));
        }

        try {
            return Files.createFile(path);
        } catch (IOException e) {
            throw new CommandException(String.format("Create new file '%1$s' IOException", path));
        }
    }
}
