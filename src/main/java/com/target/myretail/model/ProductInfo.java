package com.target.myretail.model;

public class ProductInfo {
    long id;
    String name;

    public ProductInfo(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

