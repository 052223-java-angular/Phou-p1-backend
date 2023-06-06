package com.revature.yield.utils;

public class DataTypeUtil {

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static  Double toDouble(String value) {
        return value != null && isDouble(value) ? Double.parseDouble(value) : 0.00;
    }

}
