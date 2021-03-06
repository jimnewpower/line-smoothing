package com.primalimited.smoothing.model;

public interface Coordinate {
    static Coordinate of(double x, double y) {
        return new CoordinateImpl(x, y);
    }

    static Coordinate between(Coordinate c0, Coordinate c1, float fraction) {
        double x = c0.getX() + ((c1.getX() - c0.getX()) * fraction);
        double y = c0.getY() + ((c1.getY() - c0.getY()) * fraction);
        return of(x, y);
    }

    double getX();
    double getY();

    default boolean valid() {
        return valid(getX()) && valid(getY());
    }

    static boolean valid(double value) {
        return !Double.isNaN(value) && !Double.isInfinite(value);
    }
}
