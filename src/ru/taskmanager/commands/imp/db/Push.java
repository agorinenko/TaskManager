package ru.taskmanager.commands.imp.db;

import ru.taskmanager.api.Version;
import ru.taskmanager.api.VersionsRepository;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.utils.ListUtils;
import ru.taskmanager.utils.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class Push extends SafetyCommand {
    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {

        SuccessResult result = new SuccessResult();

        KeyValueParam versionParam = ListUtils.getKeyValueParam(params, "v");
        String v = null != versionParam ? versionParam.getDefaultOrStringValue("") : "";
        boolean versionIsMissing = StringUtils.isNullOrEmpty(v) ? true : false;

        StringBuilder sb = new StringBuilder();
        sb.append("Push command:");
        StringUtils.appendLineSeparator(sb);
        StringUtils.appendLine(sb);
        StringUtils.appendLineSeparator(sb);
        sb.append("Timestamp                | Status  | Name                           ");
        StringUtils.appendLineSeparator(sb);
        StringUtils.appendLine(sb);
        StringUtils.appendLineSeparator(sb);
        VersionsRepository versionsRepository = new VersionsRepository();
        List<Version> localVersions = versionsRepository.getLocalVersions();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss S");

        for (Version version : localVersions) {
            String versionTimestamp = df.format(version.getVersionTimestamp());
            if(null != versionTimestamp) {
                Boolean isRemote = versionsRepository.isRemote(version);
                String name = StringUtils.formatString(version.getName(), 30, ' ');

                String statusStr;
                if (isRemote) {
                    statusStr = "INSTALL";
                } else{
                    if(versionIsMissing || version.getVersionTimestampString().equalsIgnoreCase(v)) {
                        int status = versionsRepository.pushItem(version);
                        if (status > 0) {
                            statusStr = "OK";
                        } else if (status == 0) {
                            statusStr = "EMPTY";
                        } else {
                            statusStr = "ERROR";
                        }
                    } else {
                        statusStr = "MISSED";
                    }
                }

                sb.append(String.format("%1$s | %2$s | %3$s ",
                        StringUtils.formatString(versionTimestamp, 24, ' '),
                        StringUtils.formatString(statusStr, 7, ' '),
                        name));

                sb.append(System.lineSeparator());
            }
        };
        StringUtils.appendLine(sb);

        String resultMessage = sb.toString();
        result.setMessage(resultMessage);
        return result;
    }
}
