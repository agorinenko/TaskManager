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

public class Delete extends BaseDbCommand {

    @Override
    public List<CommandResult> safetyExecute(List<KeyValueParam> params) throws CommandException {
        KeyValueParam versionParam = ListUtils.getKeyValueParam(params, "v");

        String version = null != versionParam ? versionParam.getDefaultOrStringValue("") : "";
        boolean versionIsMissing = StringUtils.isNullOrEmpty(version) ? true : false;

        VersionsRepository versionsRepository = initVersionsRepository(params);
        Version remoteVersion = versionIsMissing ? versionsRepository.getFirstRemoteVersion(params) :versionsRepository.getRemoteVersion(params, version);
        if(null == remoteVersion){
            throw new CommandException("Remote version '" + version + "' not found");
        }

        int status = remoteVersion.delete(params);
        String statusStr;

        if (status > 0) {
            statusStr = "OK";
        } else if (status == 0) {
            statusStr = "NOT_FOUND";
        } else {
            statusStr = "ERROR";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Operation status:" + statusStr + "(" + status + ")");
        StringUtils.appendLineSeparator(sb);

        return createSingleSuccessResult(sb.toString());
    }
}
