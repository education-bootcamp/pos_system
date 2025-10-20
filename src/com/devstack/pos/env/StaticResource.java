package com.devstack.pos.env;

public class StaticResource {
    private final static String VERSION="1.0.0";
    private final static String COMPANY="Developers Stack";
    public static String getVersion() {
        return VERSION;
    }
    public static String getCompany() {
        return COMPANY;
    }
}
