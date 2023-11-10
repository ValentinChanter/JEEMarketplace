package com.cytech.marketplace.tools;

public class CheckIntFloat {

    public static boolean checkInt(String a) {
        try {
            int test = Integer.parseInt(a);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean checkFloat(String a) {
        try {
            float test = Float.parseFloat(a);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
