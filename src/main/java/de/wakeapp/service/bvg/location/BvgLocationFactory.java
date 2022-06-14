package de.wakeapp.service.bvg.location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BvgLocationFactory {
    public static BvgLocation createFromJsonString(String jsonString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONArray(jsonString).getJSONObject(0);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
            return null;
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            return null;
        }

        String type = jsonObject.optString("type");
        if ("stop".equals(type) || "station".equals(type)) {
            return createStopBvgLocation(jsonObject);
        }

        if (jsonObject.optString("name", null) != null) {
            return createPOIBvgLocation(jsonObject);
        }

        if (jsonObject.optString("address", null) != null) {
            return createAddressBvgLocation(jsonObject);
        }

        return null;
    }

    private static BvgLocation createStopBvgLocation(JSONObject jsonObject) {
        String id = jsonObject.optString("id");
        String name = jsonObject.optString("name");

        return new BvgLocation(id, name);
    }

    private static BvgLocation createPOIBvgLocation(JSONObject jsonObject) {
        String id = jsonObject.optString("id");
        String name = jsonObject.optString("name");
        String longitude = jsonObject.optString("longitude");
        String latitude = jsonObject.optString("latitude");

        return new BvgLocation(id, name, latitude, longitude);
    }

    private static BvgLocation createAddressBvgLocation(JSONObject jsonObject) {
        String address = jsonObject.optString("address");
        String longitude = jsonObject.optString("longitude");
        String latitude = jsonObject.optString("latitude");

        return new BvgLocation(address, latitude, longitude);
    }
}
