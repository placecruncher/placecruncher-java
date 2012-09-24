package com.placecruncher.server.application;

public final class Constants
{
    public static final int ENUM_MAXLEN = 64;
    public static final int PROP_KEY_MAXLEN = 64;
    public static final int PROP_VALUE_MAXLEN = 1024;

    public static final int NAME_MAXLEN = 256;
    public static final int TITLE_MAXLEN = 512;
    public static final int DESCRIPTION_MAXLEN = 4096;
    public static final int URL_MAXLEN = 2000;
    public static final int EMAIL_MAXLEN = 320;

    public static final int ADDRESS_MAXLEN = 256;
    public static final int CITY_MAXLEN = 128;
    public static final int STATE_MAXLEN = 2;
    public static final int COUNTRY_MAXLEN = 2;
    public static final int ZIPCODE_MAXLEN = 10;
    public static final int PHONE_MAXLEN = 15;

    public static final int MESSAGE_MAXLEN = 4096;

    public static final String JSON_CONTENT = "application/json";
    public static final String HTML_CONTENT = "text/html";

    public static final String X_API_SIGNATURE = "X-API-Signature";
    public static final String X_API_TIMESTAMP = "X-API-Timestamp";
    public static final String X_API_KEY = "X-API-Key";
    public static final String X_APP_CLIENT = "X-App-Client"; // <OS>:<OS Version>:<Push Notification ID>:<UUID>:<Phone Model>
    public static final String AUTHENTICATION = "Authentication";

    private Constants() {}
}
