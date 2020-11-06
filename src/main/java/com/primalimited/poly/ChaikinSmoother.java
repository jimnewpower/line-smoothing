package com.primalimited.poly;

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
    static final float CHAIKIN_FRACTION_MINIMUM = 0.05f;
    static final float CHAIKIN_FRACTION_MAXIMUM = 0.45f;

    private final float fraction;

    public ChaikinSmoother() {
        this.fraction = CHAIKIN_FRACTION_DEFAULT;
    }

    public ChaikinSmoother(float fraction) {
        float fractionToUse = fraction;
        if (fractionToUse < CHAIKIN_FRACTION_MINIMUM)
            fractionToUse = CHAIKIN_FRACTION_MINIMUM;
        if (fractionToUse > CHAIKIN_FRACTION_MAXIMUM)
            fractionToUse = CHAIKIN_FRACTION_MAXIMUM;
        this.fraction = fractionToUse;
    }

    float getChaikinFraction() {
        return fraction;
    }

    @Override
    public Poly smooth(Poly poly) {
        Objects.requireNonNull(poly, "poly");
        if (!poly.valid())
            throw new IllegalArgumentException("Poly argument contains invalid vertices.");

        if (poly.size() < MINIMUM_POLY_N_VERTICES)
            return poly;

        List<Coordinate> original = poly.ordered();
        Poly smoothed = new PolyImpl();

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
