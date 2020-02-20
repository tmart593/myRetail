package com.target.myretail.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

/**
 * Represents a document record in productprice
 * Collection in a mongodb database - holds
 * Product price(value) and currency code.
 */
@Document(collection = "productprice")
public class ProductPrice {

    @Id
    private long id;

    private BigDecimal value;

    private CurrencyCode currencyCode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }
}
