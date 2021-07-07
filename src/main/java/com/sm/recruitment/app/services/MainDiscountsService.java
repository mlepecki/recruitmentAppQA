package com.sm.recruitment.app.services;

import com.sm.recruitment.app.enums.Periodicity;
import com.sm.recruitment.app.enums.TransactionType;
import com.sm.recruitment.app.model.Transaction;

import java.util.List;

/**
 * Below processor check if there are some transactions, if yes,
 * calling another processors to calculate and check the correct discount.
 */
public interface MainDiscountsService {

    List<Transaction> calculateTransactions(List<Transaction> transactions, TransactionType transactionType);
}
