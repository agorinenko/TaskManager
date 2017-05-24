package ru.taskmanager.commands.imp.db;

import ru.taskmanager.api.LocalVersion;
import ru.taskmanager.api.Version;
import ru.taskmanager.api.VersionComparator;
import ru.taskmanager.api.VersionsRepository;
import ru.taskmanager.api.mappers.BaseMapper;
import ru.taskmanager.api.mappers.LocalVersionManager;
import ru.taskmanager.api.mappers.VersionMapper;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.sql.DataUtils;
import ru.taskmanager.utils.StatementUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class Status extends SafetyCommand {


    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();

        StringBuilder sb = new StringBuilder();
        sb.append("Versions list:");
        sb.append(System.lineSeparator());
        sb.append("-----------------------------------------------------------");
        sb.append(System.lineSeparator());
        sb.append("Timestamp               | Local | Remote | Name");
        sb.append(System.lineSeparator());
        sb.append("-----------------------------------------------------------");
        sb.append(System.lineSeparator());

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss S");
        VersionsRepository versionsRepository = new VersionsRepository();
        List<LocalVersion> allVersions = versionsRepository.getAllVersions();

        allVersions.forEach(version -> {
            try {
                String versionTimestamp = df.format(version.getVersionTimestamp());
                Boolean isLocal = versionsRepository.isLocal(version);
                Boolean isRemote = versionsRepository.isRemote(version);

                sb.append(String.format("%1$s |   %2$s   |   %3$s    | %4$s", versionTimestamp, isLocal ? "t" : "f", isRemote ? "t" : "f", version.getVersion()));
            } catch (ParseException e) {
                sb.append(String.format("Error: %1$s", e.getMessage()));
            }
            sb.append(System.lineSeparator());
        });
        sb.append("-----------------------------------------------------------");
        sb.append(System.lineSeparator());

        String resultMessage = sb.toString();
        result.setMessage(resultMessage);
        return result;
    }
}
