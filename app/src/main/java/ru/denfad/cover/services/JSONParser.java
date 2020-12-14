package ru.denfad.cover.services;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
}