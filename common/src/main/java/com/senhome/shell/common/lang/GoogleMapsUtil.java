package com.senhome.shell.common.lang;

import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;

import java.io.IOException;

public class GoogleMapsUtil
{
    private static final String API_KEY = "AIzaSyAcvHkijEasAiT9S2RxaDSgOzS8VHH7FX8";

    public static long getDriveDist(String addrOne, String addrTwo) throws ApiException, InterruptedException, IOException
    {
        //set up key
        GeoApiContext distCalcer = new GeoApiContext.Builder()
            .apiKey(API_KEY)
            .build();

        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(distCalcer);
        DistanceMatrix result = req.origins(addrOne)
            .destinations(addrTwo)
            .mode(TravelMode.DRIVING)
            .avoid(DirectionsApi.RouteRestriction.TOLLS)
            .language("en-US")
            .await();

        return result.rows[0].elements[0].distance.inMeters;
    }
}
