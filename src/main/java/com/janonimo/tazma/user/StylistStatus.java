package com.janonimo.tazma.user;

/**
 *
 * @author JANONIMO
 */
public enum StylistStatus {
    AVAILABLE, UNAVAILABLE;


   public static StylistStatus fromString(String value) {
        StylistStatus status = null;
        for (StylistStatus stylistStatus: StylistStatus.values()) {
            if (stylistStatus.name().equalsIgnoreCase(value)) {
                status = stylistStatus;
            }
        }
        return status;
    
    }
    
}
