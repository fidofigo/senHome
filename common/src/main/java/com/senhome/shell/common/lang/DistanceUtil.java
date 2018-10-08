package com.senhome.shell.common.lang;

import static java.lang.Math.toRadians;

public class DistanceUtil
{
    public static double distanceSimplify(double lat1, double lng1, double lat2, double lng2)
    {
        // 经度差值
        double dx = lng1 - lng2;
        // 纬度差值
        double dy = lat1 - lat2;
        // 平均纬度
        double b = (lat1 + lat2) / 2.0;
        // 东西距离
        double Lx = toRadians(dx) * 6367000.0 * Math.cos(toRadians(b));
        // 南北距离
        double Ly = 6367000.0 * toRadians(dy);
        // 用平面的矩形对角距离公式计算总距离
        return Math.sqrt(Lx * Lx + Ly * Ly);
    }
}
