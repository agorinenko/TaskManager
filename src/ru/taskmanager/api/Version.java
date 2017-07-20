package ru.taskmanager.api;

import ru.taskmanager.api.mappers.BaseMapper;
import ru.taskmanager.api.mappers.DeleteVersionMapper;
import ru.taskmanager.api.mappers.InsertVersionMapper;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.sql.DataUtils;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StatementUtils;
import ru.taskmanager.utils.StringUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class Version extends Row {
    private Date version;
    private Date createdAt;
    private String createdBy;
    private String description;
    private String name;

    public Version(){}

    public Version(String name) {
        try {
            setVersionTimestamp(StringUtils.getVersionTimestamp(name));
        } catch (ParseException e) {
            setVersionTimestamp(null);
        }
        setName(name);
        setCreatedBy(SettingsUtils.getSettingsValue("author"));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getVersionTimestamp() {
        return version;
    }

    public String getVersionTimestampString() {
        return StringUtils.sdf.format(getVersionTimestamp());
    }

    public void setVersionTimestamp(Date version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int delete() throws CommandException {
        final int[] resultId = {-1};

        List<String> deleteVersionStatements = StatementUtils.getDbFolderStatements("delete_version.sql");
        String deleteVersionStatement = StatementUtils.getSingleStatement(deleteVersionStatements);
        if (!StringUtils.isNullOrEmpty(deleteVersionStatement)) {

            List<BaseMapper> sqlResult = DataUtils.createConnectionInCommandContext(conn -> {
                Object[] params = {
                        getVersionTimestampString()
                };
                return DataUtils.executeStatement(conn, deleteVersionStatement, params, () -> new DeleteVersionMapper());
            });
            if (sqlResult.size() > 0) {
                DeleteVersionMapper result = (DeleteVersionMapper) sqlResult.get(0);
                List<Row> rows = result.getResult();
                if (rows.size() > 0) {
                    Row newVersion = rows.get(0);
                    resultId[0] = newVersion.getId();
                }
            }
        }

        return resultId[0];
    }
}
