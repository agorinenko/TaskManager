package ru.taskmanager.api;

import ru.taskmanager.api.mappers.BaseMapper;
import ru.taskmanager.api.mappers.LocalVersionManager;
import ru.taskmanager.api.mappers.VersionMapper;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.sql.DataUtils;
import ru.taskmanager.utils.StatementUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class VersionsRepository {
    private VersionMapper versionMapper;

    private List<LocalVersion> remoteVersions;
    private List<LocalVersion> localVersions;
    private List<LocalVersion> allVersions;

    public VersionsRepository() throws CommandException {
        List<String> selectVersionsStatements = StatementUtils.getStatements("select_versions.sql");

        DataUtils.createConnectionInCommandContext(conn -> {
            List<BaseMapper> sqlResult = DataUtils.executeStatementsAsTransaction(conn, selectVersionsStatements, () ->  new VersionMapper());
            versionMapper = (VersionMapper) sqlResult.get(0);
        });

        List<Version> versions = versionMapper.getResult();
        remoteVersions = LocalVersionManager.convertToLocalVersions(versions);

        LocalVersionManager manager = new LocalVersionManager();
        try {
            localVersions = manager.select();
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }

        allVersions = LocalVersionManager.merge(remoteVersions, localVersions);
        allVersions.sort(Collections.reverseOrder(new VersionComparator()));
    }

    public List<LocalVersion> getRemoteVersions() {
        return this.remoteVersions;
    }

    public List<LocalVersion> getLocalVersions() {
        return this.localVersions;
    }

    public List<LocalVersion> getAllVersions() {
        return this.allVersions;
    }

    public Boolean isLocal(LocalVersion version){
        return LocalVersionManager.versionExist(this.localVersions, version);
    }

    public Boolean isRemote(LocalVersion version){
        return LocalVersionManager.versionExist(this.remoteVersions, version);
    }
}
