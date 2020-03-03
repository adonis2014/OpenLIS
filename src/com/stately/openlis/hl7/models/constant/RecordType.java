package com.stately.openlis.hl7.models.constant;

/**
 * Created by bryte on 3/24/14.
 */
public enum RecordType
{
    H("Message Header Record"),
    P("Patient Identifying Record"),
    O("Test Order Record"),
    R("Result Record"),
    C("Comment Record"),
    Q("Request Information Record"),
    S("Scientific Record"),
    M("Manufacturer Information Record");

    private String label;

    RecordType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
