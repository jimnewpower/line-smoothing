package com.primalimited.smoothing.compute;

import com.primalimited.smoothing.model.Poly;

public interface PolySmoother {
    public static PolySmoother chaikin() {
        return new ChaikinSmoother();
    }

    Poly smooth(Poly poly);
}
