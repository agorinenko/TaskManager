package ru.taskmanager.api;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;

public class VersionComparator implements Comparator<LocalVersion> {
    @Override
    public int compare(LocalVersion i1, LocalVersion i2) {
        Date d1, d2;
        try {
            d1 = i1.getVersionTimestamp();
        } catch (ParseException e) {
            return -1;
        }
        try {
            d2 = i2.getVersionTimestamp();
        } catch (ParseException e) {
            return 1;
        }
        long time1 = d1.getTime();
        long time2 = d2.getTime();

        if(time1 > time2){
            return 1;
        } else if(time1 < time2){
            return -1;
        }
        return 0;
    }
}
