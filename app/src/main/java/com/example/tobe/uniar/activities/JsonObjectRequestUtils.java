package com.example.tobe.uniar.activities;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * The JsonObjectRequestUtils class provides methods to request JSON object with a URL.
 *
 * @version 1.0
 * @since   2016-12-10
 */

public class JsonObjectRequestUtils implements Response.Listener<JSONObject>, Response.ErrorListener {
    Context appContext;
    RequestQueue Queue;
    GoogleMap mMap;
    Polyline Route;

    JsonObjectRequestUtils(Context appContext, GoogleMap mMap) {
        this.appContext = appContext;
        this.mMap = mMap;
        // initiate request Queue (for requesting direction from Google)
        Queue = Volley.newRequestQueue(appContext);
    }

    public void requestJsonObject(String url) {
        JsonObjectRequest JsoObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        Queue.add(JsoObjRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            List<LatLng> LatLngs = PolyUtil.decode(response.getJSONArray("routes").getJSONObject(0).getJSONObject("overview_polyline").getString("points"));
            Route = mMap.addPolyline(new PolylineOptions().addAll(LatLngs).color(Color.RED).width(2));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(appContext, error.toString(), Toast.LENGTH_SHORT).show();
    }

    public Polyline getRoute() {
        return Route;
    }
}
