package com.example.tobe.uniar.data;

import android.content.Context;

import com.example.tobe.uniar.R;
import com.example.tobe.uniar.customise.CustomGroundOverlay;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * GroundOverlayData Class stores ground overlay data and provide methods for ground overlays.
 *
 * @version 2.0
 * @since   2016-12-07
 */

public class GroundOverlayData {
    private static ArrayList<CustomGroundOverlay> GroundOverlays= new ArrayList<CustomGroundOverlay>();
    private static int lastId = -1;
    private boolean hidden = true;
    private GoogleMap mMap;
    private Context appContext;

    public GroundOverlayData(GoogleMap mMap, Context appContext) {
        this.mMap = mMap;
        this.appContext = appContext;
        addGroundOverlaysToMap();
    }

    /**
     * Gets a ground overlay according to id.
     * @param id A ground overlay's id
     * @return A ground overlay object
     */
    public static CustomGroundOverlay getGroundOverlay(int id) {
        return GroundOverlays.get(id);
    }

    /**
     * Hides all ground overlays.
     * If already hidden do nothing.
     */
    public void hideGroundOverlays() {
        if(!hidden) {
            for(CustomGroundOverlay GroundOverlay : GroundOverlays){
                GroundOverlay.getaGroundOverlay().setVisible(false);
                GroundOverlay.getaGroundOverlay().setClickable(false);
            }
            hidden = true;
        }
    }

    /**
     * Hides ground overlays according floor.
     * If already hidden do nothing.
     */
    public void hideGroundOverlays(int floor) {
        for(CustomGroundOverlay GroundOverlay : GroundOverlays){
            if(GroundOverlay.getFloor() == floor) {
                GroundOverlay.getaGroundOverlay().setVisible(false);
                GroundOverlay.getaGroundOverlay().setClickable(false);
                }
            }
        hidden = true;
    }

    /**
     * Shows ground overlays according to floor.
     * If already shown do nothing.
     */
    public void showGroundOverlays(int floor) {
        for(CustomGroundOverlay GroundOverlay : GroundOverlays){
            if(GroundOverlay.getFloor() == floor) {
                GroundOverlay.getaGroundOverlay().setVisible(true);
                GroundOverlay.getaGroundOverlay().setClickable(true);
            }
        }
        hidden = false;
    }

    /**
     * Creates and adds ground overlays to map
     */
    private void addGroundOverlaysToMap() {
        if(mMap!=null) {
            CustomGroundOverlay A32Overlay = new CustomGroundOverlay(
                    lastId++,
                    0, // need to consider how to store these values
                    mMap.addGroundOverlay(new GroundOverlayOptions()
                            .image(BitmapDescriptorFactory.fromResource(R.drawable.a32overlay))
                            .position(new LatLng(
                                    Double.parseDouble(appContext.getString(R.string.a32Lat)),
                                    Double.parseDouble(appContext.getString(R.string.a32Lng))),
                                    (float)18.45, (float)20.37) // need to consider how to store these values
                            .bearing(72) // need to consider how to store these values
                            .clickable(false)
                            .visible(false)),
                    appContext.getString(R.string.a32Title),
                    appContext.getString(R.string.a32Description));
            GroundOverlays.add(A32Overlay);
        }
    }
}
