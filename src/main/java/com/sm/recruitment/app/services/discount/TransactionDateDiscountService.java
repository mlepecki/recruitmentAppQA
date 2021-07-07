package com.sm.recruitment.app.services.discount;

import com.sm.recruitment.app.model.Transaction;

import java.util.List;
/**
 * Below processor add discount based on transaction date.
 * Value of discount is always 5% (can't be changed - business requirement)
 * Please see below example:
 *          Transaction: Periodicity = DAILY, amount = 100, transactionDate = 21-06-2021
 * Processor check transactionDate and periodicity:
 * if periodicity = DAILY and the transactionDate is one day before current date, processor add discount
 * if periodicity = WEEKLY and the transactionDate is in 1-week-back window before current date, processor add discount
 * if periodicity = MONTHLY and the transactionDate is in 1-month-back window before current date, processor add discount
 */

 public interface TransactionDateDiscountService {

    List<Transaction> addDiscount(List<Transaction> transactionList);

}
