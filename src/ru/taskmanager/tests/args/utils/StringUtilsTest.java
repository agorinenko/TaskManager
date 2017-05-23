package ru.taskmanager.tests.args.utils;

import org.junit.Test;
import ru.taskmanager.utils.StringUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilsTest {
    @Test
    public void getVersionTimestamp() throws ParseException {
        String str1 = "20170522124649370_file.sql";
        Date date = StringUtils.getVersionTimestamp(str1);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int h = cal.get(Calendar.HOUR_OF_DAY);
        int m = cal.get(Calendar.MINUTE);
        int s = cal.get(Calendar.SECOND);
        int ms = cal.get(Calendar.MILLISECOND);


        assertEquals(year, 2017);
        assertEquals(month, 4);//Начиная с 0
        assertEquals(day, 22);

        assertEquals(h, 12);
        assertEquals(m, 46);
        assertEquals(s, 49);
        assertEquals(ms, 370);
    }

    @Test
    public void trimStart() {
        String str1 = "//test";
        String str2 = "\\test";
        String str3 = "";
        String str4 = "test";

        assertEquals(StringUtils.trimStart(str1, "//"), "test");
        assertEquals(StringUtils.trimStart(str2, "\\\\"), "test");
        assertEquals(StringUtils.trimStart(str3, "//"), "");
        assertEquals(StringUtils.trimStart(str4, "//"), "test");
    }

    @Test
    public void trimEnd() {
        String str1 = "//test//";
        String str2 = "\\test\\";
        String str3 = "";
        String str4 = "test";

        assertEquals(StringUtils.trimEnd(str1, "//"), "//test");
        assertEquals(StringUtils.trimEnd(str2, "\\\\"), "\\test");
        assertEquals(StringUtils.trimEnd(str3, "//"), "");
        assertEquals(StringUtils.trimEnd(str4, "//"), "test");
    }

    @Test
    public void trim() {
        String str1 = "//test//";
        String str2 = "\\test\\";
        String str3 = "";
        String str4 = "test";
        String str5 = "     ";

        assertEquals(StringUtils.trim(str1, "//"), "test");
        assertEquals(StringUtils.trim(str2, "\\\\"), "test");
        assertEquals(StringUtils.trim(str3, "//"), "");
        assertEquals(StringUtils.trim(str4, "//"), "test");
        assertEquals(StringUtils.trim(str5, " "), "");
    }

    @Test
    public void isNullOrEmpty() {
        String str1 = "test";
        String str2 = "";
        String str3 = "    ";
        String str4 = null;

        assertFalse(StringUtils.isNullOrEmpty(str1));
        assertTrue(StringUtils.isNullOrEmpty(str2));
        assertTrue(StringUtils.isNullOrEmpty(str3));
        assertTrue(StringUtils.isNullOrEmpty(str4));
    }

    @Test
    public void getFileExtension() {

        String str1 = "20170522124649370_file.sql";
        String str2 = " 20170522124649370_file.sql ";
        String str3 = "    ";
        String str4 = "20170522124649370_file.sql.";
        String str5 = "20170522124649370_file.sql.sql";
        String str6 = ".sql";

        assertEquals(StringUtils.getFileExtension(str1), "sql");
        assertEquals(StringUtils.getFileExtension(str2), "sql");
        assertEquals(StringUtils.getFileExtension(str3), "");
        assertEquals(StringUtils.getFileExtension(str4), "");
        assertEquals(StringUtils.getFileExtension(str5), "sql");
        assertEquals(StringUtils.getFileExtension(str6), "sql");
    }
}
