package com.stately.openlis.hl7.models.constant;

/**
 * Created by bryte on 3/24/14.
 */
public enum Delimiter
{
//    private String fieldDelimiter;
//    private String repeatDelimiter;
//    private String componentDelimiter;
//    private String escapeDelimiter;
    FIELD("|"),
    REPEAT("\\"),
    COMPONENT("^"),
    ESCAPE("&"),
    CARRIAGE("<cr>");

    private String value;

    Delimiter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
