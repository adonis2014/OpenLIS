package com.stately.openlis.astm;

/**
 *
 * @author Edwin
 */

public enum RecordType
{
    H("Message Header"),
    P("Patient Record "),
    O("Test Order Record"),
    R("Result Record"),
    C("Comment Record"),
    Q("Request Information Record"),
    S("Scientific Record"),
    M("Manufacturer  Record"),
    L("Terminator  Record");

    private String label;

    RecordType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString()
    {
        return label;
    }
    
    
}
