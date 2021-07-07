package com.sm.recruitment.app.services.discount;

import com.sm.recruitment.app.enums.TransactionType;
import com.sm.recruitment.app.model.Transaction;

import java.util.List;

/**
 * Below processor add discount based on transaction type.
 * Values of discount are store in transaction.properties file and can be changed.
 * <p>
 * Please see below example:
 * 1. Given:
 * Transaction: Periodicity = DAILY, amount = 100, type = BANK_TRANSFER
 * 2. Then:
 * Processor checks transaction type and get discount for:
 * 100 - (100 * discount(%)) = final amount with periodicity discount
 * e.g.: (for BANK_TRANSFER):
 * 100 - (100 * 5%) = 95
 */
public interface TransactionTypeDiscountService {

    List<Transaction> addDiscount(List<Transaction> transactions, TransactionType transactionType);

}
