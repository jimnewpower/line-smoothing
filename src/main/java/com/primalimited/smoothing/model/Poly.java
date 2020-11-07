package com.primalimited.smoothing.model;

import java.util.List;

/**
 * Represents a polyline, which may or may not be a closed polygon
 */
public interface Poly {
    public static Poly create() {
        return new PolyImpl();
    }

    public static Poly create(List<Coordinate> vertices) {
        return new PolyImpl(vertices);
    }

    /**
     * Number of vertices.
     * @return number of vertices
     */
    int size();

    /**
     * Is it a closed polygon.
     * @return true if it is closed, false otherwise.
     */
    boolean isClosedPolygon();

    /**
     * Attempt to close the poly.
     */
    void closePolygon();

    /**
     * List of ordered vertices.
     * @return list of ordered vertices.
     */
    List<Coordinate> ordered();

    /**
     * Append a vertex to the poly.
     * @param coordinate the coordinate to append.
     * @return true on success, false otherwise.
     */
    boolean add(Coordinate coordinate);

    /**
     * Is the poly valid?
     * @return true if valid, false otherwise.
     */
    default boolean valid() {
        if (size() == 0)
            return true;
        return ordered().stream().allMatch(c -> c.valid());
    }
}
