package com.example.tobe.uniar;

import com.example.tobe.uniar.customise.CustomGroundOverlay;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sihanyu on 2017/4/3.
 */

public class CustomGroundOverlay_test {
    int id = 1;
    int floor = 1;
    String s1 = "Title";
    String s2 = "Description";

    CustomGroundOverlay cgo = new CustomGroundOverlay(id, floor, null, s1, s2);

    @Test
    public void customMarker_isCorrect() throws Exception{
        assertEquals(id, cgo.getId());
        assertEquals(floor, cgo.getFloor());
        assertEquals(s1, cgo.getTitle());
        assertEquals(s2, cgo.getDescription());
    }
}
