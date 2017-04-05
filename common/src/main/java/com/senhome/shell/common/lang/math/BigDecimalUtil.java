package com.senhome.shell.common.lang.math;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {

    public static Integer getPercentageInteger(Integer value) {

        if (value == null) {
            return null;
        }

        return BigDecimal.valueOf(value.longValue()).multiply(BigDecimal.valueOf(100L)).intValue();
    }

    public static Integer getPercentageInteger(String value) {

        if (StringUtils.isEmpty(value) || !NumberUtils.isNumber(value)) {
            return null;
        }

        return new BigDecimal(value).multiply(BigDecimal.valueOf(100L)).intValue();
    }

    public static Double getPercentage(Integer value) {
        return getPercentage(value, 2, RoundingMode.HALF_DOWN);
    }

    public static Double getPercentage(Integer value, int scale, RoundingMode roundingMode) {

        if (value == null) {
            return null;
        }

        return BigDecimal.valueOf(value.longValue()).divide(BigDecimal.valueOf(100L), scale, roundingMode)
                .doubleValue();
    }

    public static Double getFiveGrade(Integer value) {

        if (value == null) {
            return null;
        }

        int val1 = value / 2000;
        int val2 = value % 2000;

        String result = String.valueOf(val1);

        if (val2 >= 1000) {
            result += ".5";
        } else {
            result += ".0";
        }

        return Double.valueOf(result);

    }

}
