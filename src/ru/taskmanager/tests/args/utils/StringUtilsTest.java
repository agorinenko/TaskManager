package ru.taskmanager.tests.args.utils;

import org.junit.Test;
import ru.taskmanager.utils.StringUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilsTest {
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
}
