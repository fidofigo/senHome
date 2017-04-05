package com.senhome.shell.common.lang.string;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;

public class StringUtil {

    private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    public static String bytesToString(byte[] bytes) {

        if (ArrayUtils.isEmpty(bytes)) {
            return null;
        }

        return new String(bytes, CHARSET_UTF8);
    }

    public static String substring(String str, int maxLength) {

        return substring(str, maxLength, false);

    }

    public static String substring(String str, int maxLength, boolean appendEllipsis) {

        if (StringUtils.isEmpty(str)) {
            return str;
        }

        if (str.length() <= maxLength) {
            return str;
        }

        str = StringUtils.substring(str, 0, maxLength);

        if (!appendEllipsis || str.length() < 3) {
            return str;
        }

        str = StringUtils.substring(str, 0, str.length() - 3);

        return str + "...";

    }

}
