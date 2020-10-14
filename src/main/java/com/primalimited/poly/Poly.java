package com.primalimited.poly;

import java.util.List;

public interface Poly {
    int size();
    boolean isClosedPolygon();
    List<Coordinate> ordered();
    boolean add(Coordinate point);
}
