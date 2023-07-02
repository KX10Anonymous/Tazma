package com.janonimo.tazma.user;

/**
 *
 * @author JANONIMO
 */
public enum Role {
    ADMIN, STYLIST, CLIENT;

    public static Role fromString(String value) {
        Role r = null;
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                r = role;
            }
        }
        return r;
    }
}
