/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.janonimo.tazma.user;

/**
 *
 * @author JANONIMO
 */
public enum StylistStatus {
    AVAILABLE, UNAVAILABLE;

    /**
     * 
     * @param value
     * @return 
     */
    static StylistStatus fromString(String value) {
        StylistStatus status = null;
        for (StylistStatus s: StylistStatus.values()) {
            if (s.name().equalsIgnoreCase(value)) {
                status = s;
            }
        }
        return status;
    
    }
    
}
