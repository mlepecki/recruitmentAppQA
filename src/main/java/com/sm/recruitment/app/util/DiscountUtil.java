package com.sm.recruitment.app.util;

import com.sm.recruitment.app.model.Transaction;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.CEILING;

public class DiscountUtil {

    private static final int SCALE_VALUE = 2;

    private DiscountUtil() {}

    public static void addDiscountToSingleTransaction(int discountValue, Transaction transaction) {
        final BigDecimal transactionAmount = transaction.getAmount();
        final BigDecimal discountValueInPercent = valueOf(discountValue).movePointLeft(2);
        transaction.setAmount(transactionAmount.subtract(transactionAmount.multiply(discountValueInPercent)).setScale(SCALE_VALUE, CEILING));
    }

}
