package com.example.tobe.uniar;

import com.example.tobe.uniar.activities.MapsActivity;
import com.example.tobe.uniar.customise.CustomMarker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sihanyu on 2017/4/3.
 */

public class CustomMarker_test {
    int i = 1;
    String s1 = "TestTitle";
    String s2 = "TestDesctiption";

    CustomMarker cm = new CustomMarker(i, null, s1, s2);

    @Test
    public void customMarker_isCorrect() throws Exception{
        assertEquals(i, cm.getId());
        assertEquals(s1, cm.getTitle());
        assertEquals(s2, cm.getDescription());
    }
}