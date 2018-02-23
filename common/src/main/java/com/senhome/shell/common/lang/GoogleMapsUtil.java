package com.senhome.shell.common.lang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapsUtil
{
    private static Logger logger = LoggerFactory.getLogger(GoogleMapsUtil.class);

    private static final String API_KEY = "AIzaSyAcvHkijEasAiT9S2RxaDSgOzS8VHH7FX8";

    private static final String HEAD_URL = "https://ditu.google.cn/maps/api/distancematrix/json";

    private static final String SUCCESS = "OK";

    public static List<Integer> getDistance(String origin, String destination)
    {
        String data = "origins=" + origin + "&destinations=" + destination + "&key=" + API_KEY;

        List<Integer> distanceList = new ArrayList<>();
        try
        {
            String resultStr = HttpConnectionUtils.sendGet(HEAD_URL, data);

            JSONObject jsonObject = JSON.parseObject(resultStr);

            String status = jsonObject.getString("status");
            if(SUCCESS.equals(status))
            {
                JSONArray rows = jsonObject.getJSONArray("rows");

                for (int i = 0; i < rows.size(); i++)
                {
                    JSONArray elements = rows.getJSONObject(i).getJSONArray("elements");

                    for (int j = 0; j < elements.size(); j++)
                    {
                        JSONObject element = elements.getJSONObject(j);
                        if(SUCCESS.equals(element.getString("status")))
                        {
                            Integer distance = element.getJSONObject("distance").getInteger("value");
                            distanceList.add(distance);
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("getDriveDist error");
        }
        return distanceList;
    }
}
