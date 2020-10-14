package com.primalimited.poly;

import com.primalimited.poly.Coordinate;
import com.primalimited.poly.Poly;
import com.primalimited.poly.PolyImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class PolyTest {
    @Test
    public void testPolygon() {
        Poly original = new PolyImpl();
        original.add(Coordinate.of(0, 0));
        original.add(Coordinate.of(10, 10));
        original.add(Coordinate.of(20, 0));
        original.add(Coordinate.of(0, 0));
        assertEquals("size", 4, original.size());
        assertTrue("isClosedPolygon()", original.isClosedPolygon());
    }

    @Test
    public void testNotAPolygon() {
        Poly original = new PolyImpl();
        original.add(Coordinate.of(0, 0));
        original.add(Coordinate.of(10, 10));
        original.add(Coordinate.of(20, 0));
        assertEquals("size", 3, original.size());
        assertFalse("isClosedPolygon()", original.isClosedPolygon());
    }
}
