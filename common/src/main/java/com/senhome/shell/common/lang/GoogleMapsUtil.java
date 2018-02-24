package com.senhome.shell.common.lang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.senhome.shell.common.dal.domain.DistanceDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class GoogleMapsUtil
{
    private static Logger logger = LoggerFactory.getLogger(GoogleMapsUtil.class);

    private static final String API_KEY = "AIzaSyAcvHkijEasAiT9S2RxaDSgOzS8VHH7FX8";

    private static final String HEAD_URL = "https://ditu.google.cn/maps/api/distancematrix/json";

    private static final String SUCCESS = "OK";

    public static List<DistanceDO> getDistance(List<String> codeIds, String destination)
    {
        String origin = "";

        for(String code : codeIds)
        {
            if(Objects.equals(origin, ""))
            {
                origin += "Singapore+" + code;
            }
            else
            {
                origin += "|Singapore+" + code;
            }
        }

        String data = "origins=" + origin + "&destinations=Singapore+" + destination + "&key=" + API_KEY;

        List<DistanceDO> distanceList = new ArrayList<>();
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
                            String codeId = codeIds.get(j);
                            Integer distance = element.getJSONObject("distance").getInteger("value");
                            DistanceDO distanceDO = new DistanceDO();
                            distanceDO.setCode(codeId);
                            distanceDO.setDistance(distance);

                            distanceList.add(distanceDO);
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
