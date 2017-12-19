package ru.taskmanager.commands.imp.db;

import ru.taskmanager.api.Version;
import ru.taskmanager.api.VersionsRepository;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.utils.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Consumer;

public class Status extends BaseDbCommand {

    @Override
    public List<CommandResult> safetyExecute(List<KeyValueParam> params) throws CommandException {
        StringBuilder sb = new StringBuilder();
        sb.append("Versions list:");
        StringUtils.appendLineSeparator(sb);
        StringUtils.appendLine(sb);
        StringUtils.appendLineSeparator(sb);
        sb.append("Timestamp                | Local  | Remote | Author     | Name                           ");
        StringUtils.appendLineSeparator(sb);
        StringUtils.appendLine(sb);
        StringUtils.appendLineSeparator(sb);

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss S");
        VersionsRepository versionsRepository = initVersionsRepository(params);
        List<Version> allVersions = versionsRepository.getAllVersions();

        for (Version version : allVersions) {
            if(null != version.getVersionTimestamp()) {
                String createdBy = StringUtils.formatString(version.getCreatedBy(), 16, ' ');
                String name = StringUtils.formatString(version.getName(), 30, ' ');
                String versionTimestamp = df.format(version.getVersionTimestamp());
                Boolean isLocal = versionsRepository.isLocal(version);
                Boolean isRemote = versionsRepository.isRemote(version);

                sb.append(String.format("%1$s | %2$s | %3$s | %4$s | %5$s ",
                        StringUtils.formatString(versionTimestamp, 24, ' '),
                        StringUtils.formatString(isLocal ? "TRUE" : "FALSE", 6, ' '),
                        StringUtils.formatString(isRemote ? "TRUE" : "FALSE", 6, ' '),
                        StringUtils.formatString(createdBy, 10, ' '),
                        name));
            }
            StringUtils.appendLineSeparator(sb);
        }

        StringUtils.appendLine(sb);
        StringUtils.appendLineSeparator(sb);

        return createSingleSuccessResult(sb.toString());
    }
}
