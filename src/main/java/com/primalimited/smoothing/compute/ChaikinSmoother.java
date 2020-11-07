package com.primalimited.smoothing.compute;

import com.primalimited.smoothing.model.Coordinate;
import com.primalimited.smoothing.model.Poly;

import java.util.List;
import java.util.Objects;

/**
 * Chaikin line smoothing algorithm implementation.
 * G. M. Chaikin, “An algorithm for high-speed curve generation,
 * Computer Graphics andImage Processing,”vol. 3, 1974, pp. 346-349.
 */
public class ChaikinSmoother implements PolySmoother {
    static final int MINIMUM_POLY_N_VERTICES = 3;
    static final float CHAIKIN_FRACTION_DEFAULT = 0.25f;

    private final float fraction;

    public ChaikinSmoother() {
        this.fraction = CHAIKIN_FRACTION_DEFAULT;
    }

    @Override
    public Poly smooth(Poly poly) {
        Objects.requireNonNull(poly, "poly");
        if (!poly.valid())
            throw new IllegalArgumentException("Poly argument contains invalid vertices.");

        if (poly.size() < MINIMUM_POLY_N_VERTICES)
            return poly;

        List<Coordinate> original = poly.ordered();
        Poly smoothed = Poly.create();

        final float largeFraction = 1.f - fraction;

        int size = original.size();

        if (!poly.isClosedPolygon())
            smoothed.add(original.get(0));

        /* loop on original poly, adding line segment midpoints and adjusting vertices */
        for (int index = 0; index < size -1; index++) {
            Coordinate c0 = original.get(index);
            Coordinate c1 = original.get(index + 1);

            smoothed.add(Coordinate.between(c0, c1, fraction));
            smoothed.add(Coordinate.between(c0, c1, largeFraction));
        }

        if (!poly.isClosedPolygon())
            smoothed.add(original.get(size - 1));
        else
            smoothed.closePolygon();

        return smoothed;
    }
}
