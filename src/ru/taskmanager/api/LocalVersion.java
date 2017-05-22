package ru.taskmanager.api;

import ru.taskmanager.utils.StringUtils;

import java.text.ParseException;
import java.util.Date;

public class LocalVersion {
    private String version;

    public LocalVersion(String version) {
        this.version = version;
    }

    public Date getVersionTimestamp() throws ParseException {
        String ver = getVersion();
        Date date = StringUtils.getVersionTimestamp(ver);
        return date;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
