package com.mk.camel.model;

public class Allocation {
    String name;
    String allocation;

    @Override
    public String toString() {
        return "Allocation{" +
                "name='" + name + '\'' +
                ", allocation='" + allocation + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllocation() {
        return allocation;
    }

    public void setAllocation(String allocation) {
        this.allocation = allocation;
    }
}
