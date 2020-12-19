package ru.denfad.cover.services;

import android.graphics.Color;

import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.gson.JsonObject;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSONParser {
    public static List<LatLng> getCoordinates(String json){
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);
            JSONArray arrObj = obj.getJSONArray("routes");
            obj = arrObj.getJSONObject(0);
            obj = obj.getJSONObject("overview_polyline");
            return PolyUtil.decode(obj.getString("points"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static String getRouteInformation(String json){
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);
            JSONArray arrObj = obj.getJSONArray("routes");
            obj = arrObj.getJSONObject(0);
            arrObj = obj.getJSONArray("legs");
            obj = arrObj.getJSONObject(0);
            arrObj = obj.getJSONArray("steps");
            obj = arrObj.getJSONObject(1);
            obj = obj.getJSONObject("transit_details");
            obj = obj.getJSONObject("line");
            String s = obj.getString("short_name");
            return s;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Пешком/На такси";
    }

    public static List<PatternItem> getPattern(String type){
        if ("walking".equals(type)) {
            return Arrays.asList(new Dot(), new Gap(20));
        }
        return Arrays.asList(new Dash(20));
    }
}