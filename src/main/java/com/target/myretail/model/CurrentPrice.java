package com.target.myretail.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.StringUtils;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CurrentPrice {

    @NotNull
    @Digits(integer=13, fraction = 2)
    private BigDecimal value;
    @JsonProperty("currency_code")
    @NotNull
    private String currencyCode;

    public CurrentPrice(){
    }
    @AssertTrue(message = "must use a valid currency code")
    public boolean isValidCurrencyCode(){
        return !StringUtils.isEmpty(this.currencyCode) &&
                CurrencyCode.CURRENCY_CODES_SET.contains(this.currencyCode);
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
