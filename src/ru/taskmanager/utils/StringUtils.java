package ru.taskmanager.utils;

import ru.taskmanager.FinalConstants;
import ru.taskmanager.args.params.KeyValueParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static boolean containsIgnoreCase (String str, String subString){
        return str.toLowerCase().contains(subString.toLowerCase());
    }

    public static String trimStart(String str, String characters) {
        String pattern = "^[" + characters + "]+";
        //pattern = Pattern.quote(pattern);
        return str.replaceAll(pattern, "");
    }

    public static String trimEnd(String str, String characters) {
        String pattern = "[" + characters + "]+$";
        //pattern = Pattern.quote(pattern);
        return str.replaceAll(pattern, "");
    }

    public static String trim(String str, String characters){
        str = trimStart(str, characters);
        str = trimEnd(str, characters);
        return str;
    }

    public static String replaceAllSpecialConstants(List<KeyValueParam> params, String str){
        str = str.replaceAll("#NEW_LINE#",  System.lineSeparator());
        str = str.replaceAll("#VERSION#", FinalConstants.Version);

        String baseScriptDir = Matcher.quoteReplacement(SettingsUtils.getBaseScriptDir(params));
        str = str.replaceAll("#BASE_SCRIPT_DIR#",  baseScriptDir);

        return str;
    }

    public static String replaceAllDbConstants(List<KeyValueParam> params, String str){
        str = str.replaceAll("DATA_BASE_NAME", (String) SettingsUtils.getSettingsOrParamValue(params, "db.name"));

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

    public static String formatString(String str, int length, char character){
        if(isNullOrEmpty(str)){
            return "";
        }

        int i = length - str.length();

        if(i > 0){
            StringBuilder builder = new StringBuilder(str);

            for (int j = 0; j < i; j++){
                builder.append(character);
            }
            return builder.toString();
        } else{
            return str.substring(0, length);
        }
    }

    public static void appendLine(StringBuilder sb){
        sb.append("--------------------------------------------------------------------------------");
    }
    public static void appendLineSeparator(StringBuilder sb){
        sb.append(System.lineSeparator());
    }
}
