package com.primalimited.poly;

import java.util.Objects;

public class CoordinateImpl implements Coordinate {
    private final double x;
    private final double y;

    public CoordinateImpl(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CoordinateImpl{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinateImpl that = (CoordinateImpl) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}
