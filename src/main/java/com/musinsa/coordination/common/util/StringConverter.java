package com.musinsa.coordination.common.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class StringConverter {

    private static final NumberFormat PRICE_FORMAT = NumberFormat.getInstance(Locale.US);

    private StringConverter() {
    }

    public static String convert(BigDecimal price) {
        if (price == null) {
            return "";
        }
        return PRICE_FORMAT.format(price);
    }
}
