package com.janonimo.tazma.user;

public enum Gender {
    MALE, FEMALE, UNISEX;
    public static Gender fromString(String value) {
        Gender g = null;
        for (Gender gender : Gender.values()) {
            if (gender.name().equalsIgnoreCase(value)) {
                g = gender;
            }
        }
        return g;
    }
    public String toString(){
        switch(this){
            case MALE -> {
                return "MALE";
            }
            case FEMALE -> {
                return "FEMALE";
            }
            default -> {
                return "UNISEX";
            }
        }
    }
}
