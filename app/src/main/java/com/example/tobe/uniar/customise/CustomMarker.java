package com.example.tobe.uniar.customise;

import com.google.android.gms.maps.model.Marker;

/**
 * CustomMarker class stores a marker's id, marker object, title, description and default description,
 * with some getters and setters.
 *
 * @version 2.0
 * @since   2016-12-07
 */

public class CustomMarker {
    private int id;
    private Marker aMarker;
    private String title;
    private String description;

    /**
     * Constructor of a CustomMarker
     * @param id id of the custom marker
     * @param aMarker the marker object
     * @param title the title of the marker
     * @param description the description of the marker
     */
    public CustomMarker(int id, Marker aMarker, String title, String description) {
        this.id = id;
        this.aMarker = aMarker;
        this.title = title;
        this.description = description;
    }

    /**
     * Gets the custom marker's id.
     * @return id of custom marker
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the marker object.
     * @return marker object of the custom marker
     */
    public Marker getaMarker() {
        return aMarker;
    }

    /**
     * Gets the custom marker's title.
     * @return title of the custom marker
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the custom marker's description.
     * @return desctiption of the custom marker
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the custom marker's description.
     * @param description A string that the description will set to
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
