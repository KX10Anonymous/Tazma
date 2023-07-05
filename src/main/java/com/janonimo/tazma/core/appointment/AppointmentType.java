package com.janonimo.tazma.core.appointment;

import com.janonimo.tazma.user.Role;

public enum AppointmentType {
    CLIENT_VISIT, HOUSE_CALL;
    public static AppointmentType fromString(String value) {
        AppointmentType aType = null;
        for (AppointmentType app : AppointmentType.values()) {
            if (app.name().equalsIgnoreCase(value)) {
                aType = app;
            }
        }
        return aType;
    }
}
