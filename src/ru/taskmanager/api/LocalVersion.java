package ru.taskmanager.api;

import ru.taskmanager.utils.StringUtils;
import java.text.ParseException;
import java.util.Date;

public class LocalVersion {
    private String version;
    private Date createdAt;
    private String createdBy;
    private String description;

    public LocalVersion(String version) {
        this.version = version;
    }

    public LocalVersion(Version version) {
      this.version = version.getVersion();
      this.createdAt = version.getCreatedAt();
      this.createdBy = version.getCreatedBy();
      this.description = version.getDescription();
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getDescription() {
        return description;
    }
}
