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
        assertEquals("minimum fraction", 0.05f, ChaikinSmoother.CHAIKIN_FRACTION_MINIMUM, 1e-5);
        assertEquals("maximum fraction", 0.45f, ChaikinSmoother.CHAIKIN_FRACTION_MAXIMUM, 1e-5);
    }

    @Test
    public void perfectSquarePolygon() {
        Poly original = Poly.create();
        original.add(Coordinate.of(0, 0));
        original.add(Coordinate.of(10, 0));
        original.add(Coordinate.of(10, 10));
        original.add(Coordinate.of(0, 10));
        original.closePolygon();

        Poly smoothed = new ChaikinSmoother().smooth(original);
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

        Poly smoothed = new ChaikinSmoother().smooth(original);
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

        smoothed = new ChaikinSmoother().smooth(smoothed);
        assertEquals(12, smoothed.size());
        assertFalse(smoothed.isClosedPolygon());

        smoothed = new ChaikinSmoother().smooth(smoothed);
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

        Poly smoothed = new ChaikinSmoother().smooth(original);
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

        smoothed = new ChaikinSmoother().smooth(smoothed);
        assertEquals(13, smoothed.size());
        assertTrue(smoothed.isClosedPolygon());

        smoothed = new ChaikinSmoother().smooth(smoothed);
        assertEquals(25, smoothed.size());
        assertTrue(smoothed.isClosedPolygon());
    }

    @Test (expected = NullPointerException.class)
    public void nullPolyArgShouldThrow() {
        new ChaikinSmoother().smooth(null);
    }

    @Test
    public void tooSmallShouldReturnSame() {
        Poly original = Poly.create();
        Poly smoothed = new ChaikinSmoother().smooth(original);
        assertEquals("zero size should be equal", original, smoothed);

        original.add(Coordinate.of(0, 0));
        smoothed = new ChaikinSmoother().smooth(original);
        assertEquals("size==1 should be equal", original, smoothed);

        original.add(Coordinate.of(10, 10));
        smoothed = new ChaikinSmoother().smooth(original);
        assertEquals("size==2 should be equal", original, smoothed);
    }

    @Test
    public void customFractionBounds() {
        // fraction too small should be adjusted to the minimum
        float customFraction = 0.0f;
        ChaikinSmoother smoother = new ChaikinSmoother(customFraction);
        assertEquals(ChaikinSmoother.CHAIKIN_FRACTION_MINIMUM, smoother.getChaikinFraction(), 1e-5);

        // fraction too large should be adjusted to the maximum
        customFraction = 1.0f;
        smoother = new ChaikinSmoother(customFraction);
        assertEquals(ChaikinSmoother.CHAIKIN_FRACTION_MAXIMUM, smoother.getChaikinFraction(), 1e-5);
    }

    @Test
    public void customFraction() {
        Poly original = Poly.create();
        original.add(Coordinate.of(0, 0));
        original.add(Coordinate.of(10, 10));
        original.add(Coordinate.of(20, 0));
        original.add(Coordinate.of(0, 0));

        // sanity check: for polygon
        assertTrue(original.isClosedPolygon());

        float customFraction = 0.33f;
        ChaikinSmoother smoother = new ChaikinSmoother(customFraction);
        assertEquals(customFraction, smoother.getChaikinFraction(), 1e-5);
        Poly smoothed = smoother.smooth(original);
        assertNotNull("smoothed", smoothed);
        assertEquals(7, smoothed.size());
        assertTrue(smoothed.isClosedPolygon());

        String expected =
                "PolyImpl{vertices=[CoordinateImpl{x=3.3000001311302185, y=3.3000001311302185}, CoordinateImpl{x=6.699999570846558, y=6.699999570846558}, CoordinateImpl{x=13.300000131130219, y=6.6999998688697815}, CoordinateImpl{x=16.699999570846558, y=3.3000004291534424}, CoordinateImpl{x=13.399999737739563, y=0.0}, CoordinateImpl{x=6.600000858306885, y=0.0}, CoordinateImpl{x=3.3000001311302185, y=3.3000001311302185}]}";
        assertEquals(expected, smoothed.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidPoly() {
        Poly invalidPoly = Poly.create();
        invalidPoly.add(Coordinate.of(Double.NaN, Double.NEGATIVE_INFINITY));
        new ChaikinSmoother().smooth(invalidPoly);
    }
}
