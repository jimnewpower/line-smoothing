package com.primalimited.poly;

import com.primalimited.poly.ChaikinSmoother;
import com.primalimited.poly.Coordinate;
import com.primalimited.poly.Poly;
import com.primalimited.poly.PolyImpl;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ChaikinSmootherTest {
    @Test
    public void testChaikinPolyline() {
        Poly original = new PolyImpl();
        original.add(Coordinate.of(0, 0));
        original.add(Coordinate.of(10, 10));
        original.add(Coordinate.of(20, 0));

        // sanity check: for polyline
        assertFalse(original.isClosedPolygon());

        Poly smoothed = new ChaikinSmoother().smooth(original);
        assertNotNull("smoothed", smoothed);
        assertEquals(7, smoothed.size());

        List<Coordinate> coords = smoothed.ordered();
        int index = 0;
        assertEquals(coords.get(index).toString(), Coordinate.of(0, 0), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(2.5, 2.5), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(7.5, 7.5), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(10, 10), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(12.5, 7.5), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(17.5, 2.5), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(20, 0), coords.get(index++));

        smoothed = new ChaikinSmoother().smooth(smoothed);
        assertEquals(19, smoothed.size());
        assertFalse(smoothed.isClosedPolygon());

        smoothed = new ChaikinSmoother().smooth(smoothed);
        assertEquals(55, smoothed.size());
        assertFalse(smoothed.isClosedPolygon());
    }

    @Test
    public void testChaikinPolygon() {
        Poly original = new PolyImpl();
        original.add(Coordinate.of(0, 0));
        original.add(Coordinate.of(10, 10));
        original.add(Coordinate.of(20, 0));
        original.add(Coordinate.of(0, 0));

        // sanity check: for polygon
        assertTrue(original.isClosedPolygon());

        Poly smoothed = new ChaikinSmoother().smooth(original);
        assertNotNull("smoothed", smoothed);
        assertEquals(10, smoothed.size());
        assertTrue(smoothed.isClosedPolygon());

        List<Coordinate> coords = smoothed.ordered();
        int index = 0;
        assertEquals(coords.get(index).toString(), Coordinate.of(0, 0), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(2.5, 2.5), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(7.5, 7.5), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(10, 10), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(12.5, 7.5), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(17.5, 2.5), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(20, 0), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(15, 0), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(5, 0), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(0, 0), coords.get(index++));

        smoothed = new ChaikinSmoother().smooth(smoothed);
        assertEquals(28, smoothed.size());
        assertTrue(smoothed.isClosedPolygon());

        smoothed = new ChaikinSmoother().smooth(smoothed);
        assertEquals(82, smoothed.size());
        assertTrue(smoothed.isClosedPolygon());
    }
}
