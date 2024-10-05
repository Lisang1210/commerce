package com.qifei.common;

public enum Identify {
    INDETIFY;
    private static final String ADMIN="0";
    private static final String SUPPLIER="1";
    private static final String PURCHASER="2";

    public static String getADMIN() {
        return ADMIN;
    }

    public static String getSUPPLIER() {
        return SUPPLIER;
    }

    public static String getPURCHASER() {
        return PURCHASER;
    }
}
