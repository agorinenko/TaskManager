package ru.taskmanager.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
    public static final DateFormat sdf = new SimpleDateFormat(StringUtils.timestampFormat);
    public static final String timestampFormat = "yyyyMMddHHmmssS";

    public static  boolean isNullOrEmpty(String str){
        if(null == str){
            return true;
        }

        str = str.trim();
        return str.length() == 0;
    }

    public static String trimStart(String str, String characters){
        return str.replaceAll("^[" + characters + "]+", "");
    }

    public static String trimEnd(String str, String characters){
        return str.replaceAll("[" + characters + "]+$", "");
    }

    public static String trim(String str, String characters){
        str = trimStart(str, characters);
        str = trimEnd(str, characters);
        return str;
    }

    public static String replaceAllSpecialConstants(String str){
        str = str.replaceAll("#NEW_LINE#",  System.lineSeparator());

        return str;
    }

    public static String replaceAllDbConstants(String str){
        str = str.replaceAll("DATA_BASE_NAME",  SettingsUtils.getSettingsValue("db.name"));

        return str;
    }

    public static Date getVersionTimestamp(String version) throws ParseException {
        int separatorIndex = version.indexOf('_');
        if(separatorIndex > 0){
            String versionTimestamp = version.substring(0, separatorIndex);
            versionTimestamp = StringUtils.trim(versionTimestamp, " ");
            return sdf.parse(versionTimestamp);
        }
        throw new ParseException("Expected symbol '_'", separatorIndex);
    }

    public static String getFileExtension(String fileName) {
        int separatorIndex = fileName.lastIndexOf('.');
        if(separatorIndex >= 0) {
            String fileExtension = fileName.substring(separatorIndex + 1);
            fileExtension = StringUtils.trim(fileExtension, " ");

            return fileExtension;
        }

        return "";
    }
}
