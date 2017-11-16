package ru.taskmanager.api;

import java.util.Comparator;
import java.util.Date;

public class VersionComparator implements Comparator<Version> {
    @Override
    public int compare(Version i1, Version i2) {
        Date d1 = i1.getVersionTimestamp();
        if(null == d1){
            return 1;
        }

        Date d2 = i2.getVersionTimestamp();
        if(null == d2){
            return -1;
        }

        long time1 = d1.getTime();
        long time2 = d2.getTime();

        if(time1 > time2){
            return -1;
        } else if(time1 < time2){
            return 1;
        }
        return 0;
    }
}
