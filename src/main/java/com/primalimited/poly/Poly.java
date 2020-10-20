package com.primalimited.poly;

import java.util.List;

public interface Poly {
    int size();
    boolean isClosedPolygon();
    void closePolygon();
    List<Coordinate> ordered();
    boolean add(Coordinate point);
}
