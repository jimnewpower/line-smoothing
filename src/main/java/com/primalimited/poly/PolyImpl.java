package com.primalimited.poly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PolyImpl implements Poly {
    private List<Coordinate> vertices;

    public PolyImpl() {
        this.vertices = new ArrayList<>();
    }

    public PolyImpl(List<Coordinate> vertices) {
        this.vertices = new ArrayList<>(vertices);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PolyImpl{");
        sb.append("vertices=").append(vertices);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolyImpl poly = (PolyImpl) o;
        return Objects.equals(vertices, poly.vertices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices);
    }

    @Override
    public int size() {
        return vertices.size();
    }

    @Override
    public boolean isClosedPolygon() {
        if (size() < 2)
            return false;

        Coordinate c0 = vertices.get(0);
        Coordinate cN = vertices.get(size() - 1);
        return c0.equals(cN);
    }

    @Override
    public void closePolygon() {
        if (isClosedPolygon() || size() < 3)
            return;
        add(vertices.get(0));
    }

    @Override
    public List<Coordinate> ordered() {
        // return defensive copy
        return Collections.unmodifiableList(vertices);
    }

    @Override
    public boolean add(Coordinate coordinate) {
        if (coordinate == null)
            return false;
        // add defensive copy
        return vertices.add(Coordinate.of(coordinate.getX(), coordinate.getY()));
    }
}
