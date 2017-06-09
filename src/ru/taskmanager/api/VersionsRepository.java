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

    private List<Version> remoteVersions;
    private List<Version> localVersions;
    private List<Version> allVersions;

    public VersionsRepository() throws CommandException {
        List<String> selectVersionsStatements = StatementUtils.getDbFolderStatements("select_versions.sql");

        DataUtils.createConnectionInCommandContext(conn -> {
            List<BaseMapper> sqlResult = DataUtils.executeStatementsAsTransaction(conn, selectVersionsStatements, () ->  new VersionMapper());
            versionMapper = (VersionMapper) sqlResult.get(0);
        });

        remoteVersions = versionMapper.getResult();
        remoteVersions.sort(Collections.reverseOrder(new VersionComparator()));

        LocalVersionManager manager = new LocalVersionManager();
        try {
            localVersions = manager.select();
            localVersions.sort(Collections.reverseOrder(new VersionComparator()));
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }

        allVersions = LocalVersionManager.merge(remoteVersions, localVersions);
        allVersions.sort(Collections.reverseOrder(new VersionComparator()));
    }

    public List<Version> getRemoteVersions() {
        return this.remoteVersions;
    }

    public List<Version> getLocalVersions() {
        return this.localVersions;
    }

    public List<Version> getAllVersions() {
        return this.allVersions;
    }

    public Boolean isLocal(Version version){
        return LocalVersionManager.versionExist(this.localVersions, version);
    }

    public Boolean isRemote(Version version){
        return LocalVersionManager.versionExist(this.remoteVersions, version);
    }

    public int pushItem(Version version) throws CommandException {
        VersionMapper mapper = new VersionMapper();
        int id = mapper.insert(version);

        return id;
    }
}
