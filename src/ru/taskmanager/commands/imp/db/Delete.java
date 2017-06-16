package ru.taskmanager.commands.imp.db;

import ru.taskmanager.api.Version;
import ru.taskmanager.api.VersionsRepository;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.ListUtils;
import ru.taskmanager.utils.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class Delete extends SafetyCommand {


    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();
        KeyValueParam versionParam = ListUtils.getKeyValueParam(params, "v");

        String version = "";
        boolean versionIsMissing = false;
        if(null != versionParam){
            try {
                version = versionParam.getStringValue();
            } catch (StringIsEmptyException e) {
                versionIsMissing = true;
            }
        } else{
            versionIsMissing = true;
        }

        if(StringUtils.isNullOrEmpty(version)){
            versionIsMissing = true;
        }

        if(versionIsMissing){
            throw new CommandException("Parameter 'v' is required for command delete");
        }

        VersionsRepository versionsRepository = new VersionsRepository();
        Version remoteVersion = versionsRepository.getRemoteVersion(version);

        remoteVersion.delete();


        StringBuilder sb = new StringBuilder();
        sb.append("Versions list:");
//        StringUtils.appendLineSeparator(sb);
//        StringUtils.appendLine(sb);
//        StringUtils.appendLineSeparator(sb);
//        sb.append("Timestamp               | Local | Remote | Author           | Name                           | Description                    ");
//        StringUtils.appendLineSeparator(sb);
//        StringUtils.appendLine(sb);
//        StringUtils.appendLineSeparator(sb);
//
//        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss S");
//        VersionsRepository versionsRepository = new VersionsRepository();
//        List<Version> allVersions = versionsRepository.getAllVersions();
//
//        allVersions.forEach(version -> {
//            if(null != version.getVersionTimestamp()) {
//                String createdBy = StringUtils.formatString(version.getCreatedBy(), 16, ' ');
//                String name = StringUtils.formatString(version.getName(), 30, ' ');
//                String description = StringUtils.formatString(version.getDescription(), 30, ' ');
//                String versionTimestamp = df.format(version.getVersionTimestamp());
//                Boolean isLocal = versionsRepository.isLocal(version);
//                Boolean isRemote = versionsRepository.isRemote(version);
//
//                sb.append(String.format("%1$s |   %2$s   |   %3$s    | %4$s | %5$s | %6$s ", versionTimestamp,
//                        isLocal ? "t" : "f",
//                        isRemote ? "t" : "f",
//                        createdBy,
//                        name,
//                        description));
//            }
//            StringUtils.appendLineSeparator(sb);
//        });
//        StringUtils.appendLine(sb);
//        StringUtils.appendLineSeparator(sb);

        String resultMessage = sb.toString();
        result.setMessage(resultMessage);
        return result;
    }
}
