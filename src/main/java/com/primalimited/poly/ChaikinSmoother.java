package com.primalimited.poly;

import java.util.List;
import java.util.Objects;

public class ChaikinSmoother implements PolySmoother {
    private static final float CHAIKIN_FRACTION_DEFAULT = 0.25f;

    private final float fraction;

    public ChaikinSmoother() {
        this.fraction = CHAIKIN_FRACTION_DEFAULT;
    }

    public ChaikinSmoother(float fraction) {
        float fractionToUse = fraction;
        if (fractionToUse <= 0.f || fractionToUse >= 1.f)
            fractionToUse = CHAIKIN_FRACTION_DEFAULT;
        this.fraction = fractionToUse;
    }

    @Override
    public Poly smooth(Poly poly) {
        Objects.requireNonNull(poly, "poly");

        if (poly.size() < 1)
            return poly;

        List<Coordinate> original = poly.ordered();
        Poly smoothed = new PolyImpl();

        float largeFraction = 1.f - fraction;

        int size = original.size();
        /* loop on original poly, adding line segment midpoints and adjusting vertices */
        for (int index = 0; index < size -1; index++) {
            Coordinate c0 = original.get(index);
            Coordinate c1 = original.get(index + 1);

            smoothed.add(c0);
            smoothed.add(Coordinate.between(c0, c1, fraction));
            smoothed.add(Coordinate.between(c0, c1, largeFraction));
        }
        smoothed.add(original.get(size - 1));

        return smoothed;
    }
}
