package ru.taskmanager.api;

import ru.taskmanager.api.mappers.BaseMapper;
import ru.taskmanager.api.mappers.LocalVersionManager;
import ru.taskmanager.api.mappers.VersionMapper;
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

    private String baseDir;
    public void setBaseDir(String baseDir){
        this.baseDir = baseDir;
    }

    public List<Version> getRemoteVersions() throws CommandException {
        if(null == this.remoteVersions){
            List<String> selectVersionsStatements = StatementUtils.getDbFolderStatements("select_versions.sql");

            List<BaseMapper> sqlResult = DataUtils.createConnectionInCommandContext(conn -> {
                return DataUtils.executeStatementsAsTransaction(conn, selectVersionsStatements, () ->  new VersionMapper());
            });

            VersionMapper versionMapper = (VersionMapper) sqlResult.get(0);

            remoteVersions = versionMapper.getResult();
            remoteVersions.sort(new VersionComparator());
        }

        return this.remoteVersions;
    }

    public List<Version> getLocalVersions() throws CommandException {
        return getLocalVersions("");
    }
    public List<Version> getLocalVersions(String extension) throws CommandException {
        if(null == this.localVersions){
            LocalVersionManager manager = new LocalVersionManager();
            if(!StringUtils.isNullOrEmpty(this.baseDir)){
                manager.setBaseDir(this.baseDir);
            }

            try {
                localVersions = manager.select(extension);
                localVersions.sort(new VersionComparator());
            } catch (IOException e) {
                throw new CommandException(e.getMessage());
            }
        }

        return this.localVersions;
    }

    public List<Version> getAllVersions() throws CommandException {
        if(null == this.allVersions){
            allVersions = LocalVersionManager.merge(getRemoteVersions(), getLocalVersions());
            allVersions.sort(new VersionComparator());
        }

        return this.allVersions;
    }

    public Boolean isLocal(Version version) throws CommandException {
        return LocalVersionManager.versionExist(this.getLocalVersions(), version);
    }

    public Boolean isRemote(Version version) throws CommandException {
        return LocalVersionManager.versionExist(this.getRemoteVersions(), version);
    }

    public int pushItem(Version version) throws CommandException {
        VersionMapper mapper = new VersionMapper();
        int id = mapper.insert(version);

        return id;
    }

    public Version getRemoteVersion(String version) throws CommandException {
        List<Version> remoteVersions = getRemoteVersions();
        Version remoteVersion = remoteVersions.stream().filter(i -> i.getVersionTimestampString().equalsIgnoreCase(version)).findFirst().orElse(null);

        return remoteVersion;
    }

    public Version getFirstRemoteVersion() throws CommandException {
        List<Version> remoteVersions = getRemoteVersions();
        Version remoteVersion = remoteVersions.stream().findFirst().orElse(null);

        return remoteVersion;
    }
}
