package ru.taskmanager.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Version extends Row {
    private String version;
    private Date createdAt;
    private String createdBy;
    private String description;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    protected void mapFields(ResultSet rs) throws SQLException {
        setVersion(rs.getString("version"));
        setDescription(rs.getString("description"));
        setCreatedBy(rs.getString("created_by"));
        setCreatedAt(rs.getDate("created_at"));
    }
}
