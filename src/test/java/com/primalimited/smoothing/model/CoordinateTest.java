package com.primalimited.smoothing.model;

import com.primalimited.smoothing.model.Coordinate;
import com.primalimited.smoothing.model.Poly;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateTest {
    @Test
    public void testEquality() {
        Coordinate c0 = Coordinate.of(12.5, 10.0);
        Coordinate c1 = Coordinate.of(12.5, 10.0);
        assertTrue("equals()", c0.equals(c1));
        assertTrue("equals()", c1.equals(c0));
    }

    @Test
    public void testInequality() {
        Coordinate c0 = Coordinate.of(0.0, 10.0);
        assertFalse("equals(null)", c0.equals(null));
        assertFalse("equals(wrong type)", c0.equals(Poly.create()));
    }

    @Test
    public void testInequalityX() {
        Coordinate c0 = Coordinate.of(0.0, 10.0);
        Coordinate c1 = Coordinate.of(20.0, 10.0);
        assertFalse("equals()", c0.equals(c1));
        assertFalse("equals()", c1.equals(c0));
    }

    @Test
    public void testInequalityY() {
        Coordinate c0 = Coordinate.of(14.33, 10.0);
        Coordinate c1 = Coordinate.of(14.33, 20.0);
        assertFalse("equals()", c0.equals(c1));
        assertFalse("equals()", c1.equals(c0));
    }

    @Test
    public void testInequalityXandY() {
        Coordinate c0 = Coordinate.of(1.2, 10.7);
        Coordinate c1 = Coordinate.of(5.4, 15.6);
        assertFalse("equals()", c0.equals(c1));
        assertFalse("equals()", c1.equals(c0));
    }

    @Test
    public void between() {
        Coordinate c0 = Coordinate.of(0.0, 10.0);
        Coordinate c1 = Coordinate.of(20.0, 10.0);
        Coordinate c = Coordinate.between(c0, c1, 0.5f);
        assertEquals(Coordinate.of(10, 10), c);
    }

    @Test
    public void betweenSameCoordinates() {
        Coordinate c0 = Coordinate.of(0.0, 10.0);
        Coordinate c1 = Coordinate.of(0.0, 10.0);
        Coordinate c = Coordinate.between(c0, c1, 0.5f);
        assertEquals(Coordinate.of(0, 10), c);
    }

    @Test
    public void betweenZeroFraction() {
        Coordinate c0 = Coordinate.of(0.0, 10.0);
        Coordinate c1 = Coordinate.of(10.0, 20.0);
        Coordinate c = Coordinate.between(c0, c1, 0.f);
        assertEquals(Coordinate.of(0, 10), c);
    }

    @Test
    public void betweenOneFraction() {
        Coordinate c0 = Coordinate.of(0.0, 10.0);
        Coordinate c1 = Coordinate.of(10.0, 20.0);
        Coordinate c = Coordinate.between(c0, c1, 1.f);
        assertEquals(Coordinate.of(10, 20), c);
    }

    @Test
    public void betweenFractionOutOfBounds() {
        Coordinate c0 = Coordinate.of(0.0, 10.0);
        Coordinate c1 = Coordinate.of(10.0, 20.0);
        Coordinate c = Coordinate.between(c0, c1, 10.f);
        assertEquals(Coordinate.of(100, 110), c);
        c = Coordinate.between(c0, c1, -10.f);
        assertEquals(Coordinate.of(-100, -90), c);
    }

    @Test
    public void invalidNaN() {
        Coordinate c0 = Coordinate.of(Double.NaN, 0);
        assertFalse("NaN x should be invalid", c0.valid());

        c0 = Coordinate.of(0, Double.NaN);
        assertFalse("NaN y should be invalid", c0.valid());
    }

    @Test
    public void invalidInfinity() {
        Coordinate c0 = Coordinate.of(Double.POSITIVE_INFINITY, 0);
        assertFalse("NaN x should be invalid", c0.valid());

        c0 = Coordinate.of(0, Double.POSITIVE_INFINITY);
        assertFalse("NaN y should be invalid", c0.valid());

        c0 = Coordinate.of(Double.NEGATIVE_INFINITY, 0);
        assertFalse("NaN x should be invalid", c0.valid());

        c0 = Coordinate.of(0, Double.NEGATIVE_INFINITY);
        assertFalse("NaN y should be invalid", c0.valid());
    }

    @Test
    public void valid() {
        Coordinate c0 = Coordinate.of(0, 0);
        assertTrue("Should be valid", c0.valid());
    }
}
