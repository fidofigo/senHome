package com.senhome.shell.common.lang;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeoUtils
{
    /**
     * 检查一个坐标是否在多边形内
     * @param polygonPoints 多边形边界的经纬度数组
     * @return
     */
    public static boolean isPointInPolygon(double x, double y, Map<Double, Double> polygonPoints)
    {
        Point2D.Double geoPoint = buildPoint(x, y);
        List<Point2D.Double> geoPolygon = buildPolygon(polygonPoints);
        return GeoUtils.isPointInPolygon(geoPoint, geoPolygon);
    }

    /**
     * 检查一个坐标是否在多边形内
     * @param point 检查的点坐标
     * @param polygon 参照的多边形
     * @return
     */
    public static boolean isPointInPolygon(Point2D.Double point, List<Point2D.Double> polygon)
    {
        GeneralPath p = new GeneralPath();

        Point2D.Double first = polygon.get(0);
        p.moveTo(first.x, first.y);
        polygon.remove(0);

        polygon.forEach(d -> p.lineTo(d.x, d.y));

        p.lineTo(first.x, first.y);

        p.closePath();

        return p.contains(point);
    }

    /**
     * 构建一个坐标点
     * @return
     */
    public static Point2D.Double buildPoint(double x, double y)
    {
        return new Point2D.Double(x, y);
    }

    /**
     * 构建一个多边形
     * @param polygonPoints
     * @return
     */
    public static List<Point2D.Double> buildPolygon(Map<Double, Double> polygonPoints)
    {
        List<Point2D.Double> geoPolygon = new ArrayList<>();

        for (Map.Entry<Double, Double> entry : polygonPoints.entrySet())
        {
            geoPolygon.add(buildPoint(entry.getKey(), entry.getValue()));
        }

        return geoPolygon;
    }

    /**
     * 判断一个点是否在圆形区域内
     *
     * @param pointLon 要判断的点的纵坐标
     * @param pointLat 要判断的点的横坐标
     * @param lon 圆心纵坐标
     * @param lat 圆心横坐标
     * @param radius 圆的半径
     * @return
     */
    public static boolean isInCircle(double pointLon, double pointLat, double lon, double lat, String radius)
    {
        double distance = Math.hypot((pointLon - lon), (pointLat - lat));
        double r = Double.parseDouble(radius);
        if (distance >= r)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
