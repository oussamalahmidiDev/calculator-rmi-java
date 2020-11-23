package com.learn;

public class ConversionService {
    public static double convert(Conversion conversion) {
        double result = conversion.getValue() * 1.5;

        return result;
    }
}
