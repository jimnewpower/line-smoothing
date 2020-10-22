package com.primalimited.poly;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PolyTest {
    @Test
    public void testConstruction() {
        List<Coordinate> vertices = new ArrayList<>();
        Coordinate coordinate = Coordinate.of(1, 2);
        vertices.add(coordinate);
        coordinate = Coordinate.of(11, 22);
        vertices.add(coordinate);
        coordinate = Coordinate.of(14, 23);
        vertices.add(coordinate);

        Poly poly = new PolyImpl(vertices);
        assertEquals(3, poly.size());

        List<Coordinate> coordinates = poly.ordered();
        int index = 0;
        assertEquals(vertices.get(index), coordinates.get(index++));
        assertEquals(vertices.get(index), coordinates.get(index++));
        assertEquals(vertices.get(index), coordinates.get(index++));
    }

    @Test
    public void testToString() {
        Coordinate coordinate = Coordinate.of(1, 2);

        Poly poly0 = new PolyImpl();
        poly0.add(coordinate);

        String expected = "PolyImpl{vertices=[CoordinateImpl{x=1.0, y=2.0}]}";
        assertEquals("toString() output", expected, poly0.toString());
    }

    @Test
    public void testEquals() {
        Coordinate sharedCoordinate = Coordinate.of(3, 6);

        Poly poly0 = new PolyImpl();
        poly0.add(sharedCoordinate);

        Poly poly1 = new PolyImpl();
        poly1.add(sharedCoordinate);

        assertTrue("should be equal", poly0.equals(poly1));
        assertTrue("should be equal", poly1.equals(poly0));
    }

    @Test
    public void testInequality() {
        Poly poly = new PolyImpl();
        assertFalse("equals(null)", poly.equals(null));
        assertFalse("equals(wrong type)", poly.equals(Coordinate.of(0, 0)));
    }

    @Test
    public void testHashCode() {
        Coordinate sharedCoordinate = Coordinate.of(3, 6);

        Poly poly0 = new PolyImpl();
        poly0.add(sharedCoordinate);

        Poly poly1 = new PolyImpl();
        poly1.add(sharedCoordinate);

        assertEquals("should be equal", poly0.hashCode(), poly1.hashCode());

        assertNotEquals("should be different", poly0.hashCode(), new PolyImpl().hashCode());
    }

    @Test
    public void testNotEquals() {
        Coordinate c0 = Coordinate.of(3, 6);
        Poly poly0 = new PolyImpl();
        poly0.add(c0);

        Coordinate c1 = Coordinate.of(4, 8);
        Poly poly1 = new PolyImpl();
        poly1.add(c1);

        assertFalse("should not be equal", poly0.equals(poly1));
        assertFalse("should not be equal", poly1.equals(poly0));
    }

    @Test
    public void testAddNullVertex() {
        Poly original = new PolyImpl();
        original.add(Coordinate.of(0, 0));
        assertEquals("size", 1, original.size());

        original.add(null);
        assertEquals("size", 1, original.size());
    }

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

    @Test
    public void testIsClosedPolygonEdgeCases() {
        Poly original = new PolyImpl();
        assertFalse("polygon with zero vertices cannot be closed", original.isClosedPolygon());

        original.add(Coordinate.of(3, 6));
        assertFalse("polygon with one vertex cannot be closed", original.isClosedPolygon());
    }

    @Test
    public void testAlreadyClosedPolygonShouldNotAddVertex() {
        Poly original = new PolyImpl();
        // create a simple polygon (triangle)
        original.add(Coordinate.of(0, 0));
        original.add(Coordinate.of(5, 5));
        original.add(Coordinate.of(10, 0));
        original.closePolygon();
        assertTrue("should be closed", original.isClosedPolygon());
        assertEquals("size", 4, original.size());

        // explicitly call closePolygon()
        original.closePolygon();
        // and assert that polygon size (n vertices) did not change
        assertEquals("size", 4, original.size());
    }

    @Test
    public void testInvalidPolygonShouldNotClose() {
        // An invalid polygon (non-closable) has less than 3 vertices
        Poly original = new PolyImpl();

        // zero vertices
        assertEquals("size", 0, original.size());
        original.closePolygon();
        assertEquals("size", 0, original.size());

        original.add(Coordinate.of(0, 0));
        // one vertex
        assertEquals("size", 1, original.size());
        original.closePolygon();
        assertEquals("size", 1, original.size());

        original.add(Coordinate.of(10, 10));
        // two vertices
        assertEquals("size", 2, original.size());
        original.closePolygon();
        assertEquals("size", 2, original.size());
    }
}
