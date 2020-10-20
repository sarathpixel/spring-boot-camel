package com.mk.camel.model;

public enum AllocationCategory {
    NURSERY("Nursery"),
    RECEPTION("reception"),
    JUNIORS("juniors"),
    SENIORS("seniors"),
    OTHERS("others");

    private final String code;

    AllocationCategory(String category) {
        this.code = category;
    }

    public String getCode() {
        return code;
    }
}
