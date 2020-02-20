package com.target.myretail.model;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * basic class for listing currency codes - should be made more
 * complete at some point depending on business needs.
 *
 */
public enum CurrencyCode {
    EUR("EUR"),
    USD("USD"),
    GBP("GBP");

    public static final Set<String> CURRENCY_CODES_SET = Stream.of(CurrencyCode.values())
            .map(CurrencyCode::getCode)
            .collect(Collectors.toSet());

    private String code;

    CurrencyCode(String code) {
        this.code=code;
    }

    public String getCode() {
        return code;
    }

}
