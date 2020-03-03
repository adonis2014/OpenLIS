package com.stately.openlis.hl7.models.constant;


public enum TransmissionProtocol {
    SERIAL_PORT,
    TCP_IP,
    UDP;

   public static TransmissionProtocol getEnum(String name) {
        for (TransmissionProtocol transmissionProtocol : values()) {
            if (transmissionProtocol.name().equals(name)) {
                return transmissionProtocol;
            }
        }
        return null;
    }
}
