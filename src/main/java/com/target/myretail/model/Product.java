package com.target.myretail.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Product implements Serializable {
    private static final long serialVersion = 1;

    @NotNull
    @Positive
    private long id;
    @NotBlank
    private String name;

    @NotNull
    @JsonProperty("current_price")
    @Valid
    private CurrentPrice currentPrice;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CurrentPrice getCurrentPrice() {
        return currentPrice;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentPrice(CurrentPrice currentPrice) {
        this.currentPrice = currentPrice;
    }
}
