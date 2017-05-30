package com.example.tobe.uniar.data;

import android.content.Context;

import com.example.tobe.uniar.R;
import com.example.tobe.uniar.customise.CustomMarker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * MarkerData Class stores marker location data and provide methods for markers.
 *
 * @version 2.0
 * @since   2016-12-07
 */

public class MarkerData {
    private static ArrayList<CustomMarker> Markers = new ArrayList<CustomMarker>();
    private static int lastId = -1;
    private boolean hidden = false;
    private GoogleMap mMap;
    private Context appContext;

    /**
     * Constructor of the MarkerData
     * @param mMap the map to add markers to
     */
    public MarkerData(GoogleMap mMap, Context appContext) {
        this.appContext = appContext;
        this.mMap = mMap;
        // Add markers to the map.
        addMarkersToMap();
    }

    /**
     * Gets a marker according to id.
     * @param id A marker's id
     * @return A marker object
     */
    public static CustomMarker getMarker (int id) {
        return Markers.get(id);
    }

    /**
     * Hides all markers.
     * If already hidden do nothing.
     */
    public void hideMarkers() {
        if(!hidden) {
            for (CustomMarker Marker : Markers) {
                Marker.getaMarker().setVisible(false);
            }
            hidden = true;
        }
    }

    /**
     * Shows all markers.
     * If already shown do nothing.
     */
    public void showMarkers() {
        if(hidden) {
            for (CustomMarker Marker : Markers) {
                Marker.getaMarker().setVisible(true);
            }
            hidden = false;
        }
    }

    /**
     * Creates and adds markers to the map
     */
    private void addMarkersToMap() {
        if(mMap != null) {
            CustomMarker mComputerScience = new CustomMarker(
                    lastId++,
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(appContext.getString(R.string.computerScienceBuildingLat)),
                                    Double.parseDouble(appContext.getString(R.string.computerScienceBuildingLng))))
                            .title(appContext.getString(R.string.computerScienceBuildingTitle))
                            .snippet(appContext.getString(R.string.snippet))),
                    appContext.getString(R.string.computerScienceBuildingTitle),
                    appContext.getString(R.string.computerScienceBuildingDescription));
            Markers.add(mComputerScience);

            CustomMarker mExchangeBuilding = new CustomMarker(
                    lastId++,
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(appContext.getString(R.string.exchangeBuildingLat)),
                                    Double.parseDouble(appContext.getString(R.string.exchangeBuildingLng))))
                            .title(appContext.getString(R.string.exchangeBuildingTitle))
                            .snippet(appContext.getString(R.string.snippet))),
                            appContext.getString(R.string.exchangeBuildingTitle),
                            appContext.getString(R.string.exchangeBuildingDescription));
            Markers.add(mExchangeBuilding);

        }
    }

}
