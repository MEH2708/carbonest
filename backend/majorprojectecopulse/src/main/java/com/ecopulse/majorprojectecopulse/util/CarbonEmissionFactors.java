package com.ecopulse.majorprojectecopulse.util;

import java.util.Map;

public class CarbonEmissionFactors {

    public static final Map<String, Double> FACTORS = Map.of(
            "transport", 0.21,
            "electricity", 0.82,
            "food", 2.5,
            "default", 0.5
    );
}