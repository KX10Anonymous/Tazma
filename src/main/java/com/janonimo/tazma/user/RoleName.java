package com.janonimo.tazma.user;

/**
 *
 * @author JANONIMO
 */
public enum RoleName {
    ADMIN, STYLIST, CLIENT;

    public static RoleName fromString(String value) {
        RoleName r = null;
        for (RoleName roleName : RoleName.values()) {
            if (roleName.name().equalsIgnoreCase(value)) {
                r = roleName;
            }
        }
        return r;
    }
}
