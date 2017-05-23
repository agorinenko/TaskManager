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

    public String getName(){
        String ver = getVersion();

        int separatorIndex1 = ver.indexOf('_');
        int separatorIndex2 = ver.lastIndexOf('.');

        if(separatorIndex1 > 0 && separatorIndex2 > separatorIndex1){
            return ver.substring(separatorIndex1, separatorIndex2);
        }

        return "";
    }

    public String getExtension(){
        String ver = getVersion();
        return StringUtils.getFileExtension(ver);
    }

    public String getVersion() {
        return version;
    }
}
