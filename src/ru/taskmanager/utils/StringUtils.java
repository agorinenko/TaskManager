package ru.taskmanager.utils;

public class StringUtils {
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
}
