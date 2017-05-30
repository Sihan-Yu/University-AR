package com.example.tobe.uniar.customise;

import com.google.android.gms.maps.model.GroundOverlay;

/**
 * GroundOverlayData Class stores ground overlay data and provide methods for ground overlays.
 *
 * @version 2.0
 * @since   2016-12-07
 */

public class CustomGroundOverlay {
    private int id;
    private int floor;
    private GroundOverlay aGroundOverlay;
    private String title;
    private String description;

    /**
     * Constructor of a CustomGroundOverlay
     * @param id id of the custom ground overlay
     * @param aGroundOverlay the ground overlay object
     * @param title the title of the ground overlay
     * @param description the description of the ground overlay
     */
    public CustomGroundOverlay(int id, int floor, GroundOverlay aGroundOverlay, String title, String description) {
        this.id = id;
        this.floor = floor;
        this.aGroundOverlay = aGroundOverlay;
        this.title = title;
        this.description = description;
    }

    /**
     * Gets the custom ground overlay's id.
     * @return id of custom ground overlay
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the custom ground overlay's floor.
     * @return floor of custom ground overlay
     */
    public int getFloor() {
        return floor;
    }

    /**
     * Gets the ground overlay object.
     * @return GroundOverlay object of the custom ground overlay
     */
    public GroundOverlay getaGroundOverlay() {
        return aGroundOverlay;
    }

    /**
     * Gets the custom ground overlay's title.
     * @return title of the custom ground overlay
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the custom ground overlay's description.
     * @return desctiption of the custom ground overlay
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the custom ground overlay's description.
     * @param description A string that the description will set to
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
