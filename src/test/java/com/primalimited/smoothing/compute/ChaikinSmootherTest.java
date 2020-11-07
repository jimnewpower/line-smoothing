package com.primalimited.smoothing.compute;

import com.primalimited.smoothing.model.Coordinate;
import com.primalimited.smoothing.model.Poly;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ChaikinSmootherTest {
    @Test
    public void testConstants() {
        assertEquals("minimum vertices", 3, ChaikinSmoother.MINIMUM_POLY_N_VERTICES);
        assertEquals("default fraction", 0.25f, ChaikinSmoother.CHAIKIN_FRACTION_DEFAULT, 1e-5);
    }

    @Test
    public void perfectSquarePolygon() {
        Poly original = Poly.create();
        original.add(Coordinate.of(0, 0));
        original.add(Coordinate.of(10, 0));
        original.add(Coordinate.of(10, 10));
        original.add(Coordinate.of(0, 10));
        original.closePolygon();

        Poly smoothed = PolySmoother.chaikin().smooth(original);
        List<Coordinate> vertices = smoothed.ordered().stream().collect(Collectors.toList());
        assertEquals(9, vertices.size());
        int index = 0;
        assertEquals(Coordinate.of(2.5, 0), vertices.get(index++));
        assertEquals(Coordinate.of(7.5, 0), vertices.get(index++));
        assertEquals(Coordinate.of(10, 2.5), vertices.get(index++));
        assertEquals(Coordinate.of(10, 7.5), vertices.get(index++));
        assertEquals(Coordinate.of(7.5, 10), vertices.get(index++));
        assertEquals(Coordinate.of(2.5, 10), vertices.get(index++));
        assertEquals(Coordinate.of(0, 7.5), vertices.get(index++));
        assertEquals(Coordinate.of(0, 2.5), vertices.get(index++));
        assertEquals(Coordinate.of(2.5, 0), vertices.get(index++));
    }

    @Test
    public void testChaikinPolyline() {
        Poly original = Poly.create();
        original.add(Coordinate.of(0, 0));
        original.add(Coordinate.of(10, 10));
        original.add(Coordinate.of(20, 0));

        // sanity check: for polyline
        assertFalse(original.isClosedPolygon());

        Poly smoothed = PolySmoother.chaikin().smooth(original);
        assertNotNull("smoothed", smoothed);
        assertEquals(6, smoothed.size());

        List<Coordinate> coords = smoothed.ordered();
        int index = 0;
        assertEquals(coords.get(index).toString(), Coordinate.of(0, 0), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(2.5, 2.5), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(7.5, 7.5), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(12.5, 7.5), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(17.5, 2.5), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(20, 0), coords.get(index++));

        smoothed = PolySmoother.chaikin().smooth(smoothed);
        assertEquals(12, smoothed.size());
        assertFalse(smoothed.isClosedPolygon());

        smoothed = PolySmoother.chaikin().smooth(smoothed);
        assertEquals(24, smoothed.size());
        assertFalse(smoothed.isClosedPolygon());
    }

    @Test
    public void testChaikinPolygon() {
        Poly original = Poly.create();
        original.add(Coordinate.of(0, 0));
        original.add(Coordinate.of(10, 10));
        original.add(Coordinate.of(20, 0));
        original.add(Coordinate.of(0, 0));

        // sanity check: for polygon
        assertTrue(original.isClosedPolygon());

        Poly smoothed = PolySmoother.chaikin().smooth(original);
        assertNotNull("smoothed", smoothed);
        assertEquals(7, smoothed.size());
        assertTrue(smoothed.isClosedPolygon());

        List<Coordinate> coords = smoothed.ordered();
        int index = 0;
        assertEquals(coords.get(index).toString(), Coordinate.of(2.5, 2.5), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(7.5, 7.5), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(12.5, 7.5), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(17.5, 2.5), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(15, 0), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(5, 0), coords.get(index++));
        assertEquals(coords.get(index).toString(), Coordinate.of(2.5, 2.5), coords.get(index++));

        smoothed = PolySmoother.chaikin().smooth(smoothed);
        assertEquals(13, smoothed.size());
        assertTrue(smoothed.isClosedPolygon());

        smoothed = PolySmoother.chaikin().smooth(smoothed);
        assertEquals(25, smoothed.size());
        assertTrue(smoothed.isClosedPolygon());
    }

    @Test (expected = NullPointerException.class)
    public void nullPolyArgShouldThrow() {
        PolySmoother.chaikin().smooth(null);
    }

    @Test
    public void tooSmallShouldReturnSame() {
        Poly original = Poly.create();
        Poly smoothed = PolySmoother.chaikin().smooth(original);
        assertEquals("zero size should be equal", original, smoothed);

        original.add(Coordinate.of(0, 0));
        smoothed = PolySmoother.chaikin().smooth(original);
        assertEquals("size==1 should be equal", original, smoothed);

        original.add(Coordinate.of(10, 10));
        smoothed = PolySmoother.chaikin().smooth(original);
        assertEquals("size==2 should be equal", original, smoothed);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidPoly() {
        Poly invalidPoly = Poly.create();
        invalidPoly.add(Coordinate.of(Double.NaN, Double.NEGATIVE_INFINITY));
        PolySmoother.chaikin().smooth(invalidPoly);
    }
}
