package ru.taskmanager.api;

import ru.taskmanager.api.mappers.BaseMapper;
import ru.taskmanager.api.mappers.LocalVersionManager;
import ru.taskmanager.api.mappers.VersionMapper;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.sql.DataUtils;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StatementUtils;
import ru.taskmanager.utils.StringUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class VersionsRepository {
    private List<Version> remoteVersions;
    private List<Version> localVersions;
    private List<Version> allVersions;

    public List<Version> getRemoteVersions(List<KeyValueParam> params) throws CommandException {
        if(null == this.remoteVersions){
            List<String> selectVersionsStatements = StatementUtils.getDbFolderStatements(params, "select_versions.sql");

            List<BaseMapper> sqlResult = DataUtils.createConnectionInCommandContext(params, conn -> {
                return DataUtils.executeStatementsAsTransaction(conn, selectVersionsStatements, () ->  new VersionMapper());
            });

            VersionMapper versionMapper = (VersionMapper) sqlResult.get(0);

            remoteVersions = versionMapper.getResult();
            remoteVersions.sort(new VersionComparator());
        }

        return this.remoteVersions;
    }

    public List<Version> getLocalVersions(List<KeyValueParam> params) throws CommandException {
        return getLocalVersions(params, "");
    }
    public List<Version> getLocalVersions(List<KeyValueParam> params, String extension) throws CommandException {
        if(null == this.localVersions){
            LocalVersionManager manager = new LocalVersionManager(params);

            try {
                localVersions = manager.select(extension);
                localVersions.sort(new VersionComparator());
            } catch (IOException e) {
                throw new CommandException(e.getMessage());
            }
        }

        return this.localVersions;
    }

    public List<Version> getAllVersions(List<KeyValueParam> params) throws CommandException {
        if(null == this.allVersions){
            allVersions = LocalVersionManager.merge(getRemoteVersions(params), getLocalVersions(params));
            allVersions.sort(new VersionComparator());
        }

        return this.allVersions;
    }

    public Boolean isLocal(List<KeyValueParam> params, Version version) throws CommandException {
        return LocalVersionManager.versionExist(this.getLocalVersions(params), version);
    }

    public Boolean isRemote(List<KeyValueParam> params, Version version) throws CommandException {
        return LocalVersionManager.versionExist(this.getRemoteVersions(params), version);
    }

    public int pushItem(List<KeyValueParam> params, Version version) throws CommandException {
        VersionMapper mapper = new VersionMapper();
        int id = mapper.insert(params, version);

        return id;
    }

    public Version getRemoteVersion(List<KeyValueParam> params, String version) throws CommandException {
        List<Version> remoteVersions = getRemoteVersions(params);
        Version remoteVersion = remoteVersions.stream().filter(i -> i.getVersionTimestampString().equalsIgnoreCase(version)).findFirst().orElse(null);

        return remoteVersion;
    }

    public Version getFirstRemoteVersion(List<KeyValueParam> params) throws CommandException {
        List<Version> remoteVersions = getRemoteVersions(params);
        Version remoteVersion = remoteVersions.stream().findFirst().orElse(null);

        return remoteVersion;
    }
}
