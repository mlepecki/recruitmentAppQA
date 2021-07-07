package com.sm.recruitment.app.services.impl;

import com.sm.recruitment.app.enums.TransactionType;
import com.sm.recruitment.app.model.Transaction;
import com.sm.recruitment.app.services.MainDiscountsService;
import com.sm.recruitment.app.services.discount.TransactionDateDiscountService;
import com.sm.recruitment.app.services.discount.TransactionTypeDiscountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@Slf4j
public class MainDiscountsServiceImpl implements MainDiscountsService {

    private final TransactionTypeDiscountService transactionTypeDiscountService;
    private final TransactionDateDiscountService transactionDateDiscountService;

    public MainDiscountsServiceImpl(TransactionTypeDiscountService transactionTypeDiscountService, TransactionDateDiscountService transactionDateDiscountService) {
        this.transactionTypeDiscountService = transactionTypeDiscountService;
        this.transactionDateDiscountService = transactionDateDiscountService;
    }

    @Override
    public List<Transaction> calculateTransactions(List<Transaction> transactions, TransactionType transactionType) {

        if (isEmpty(transactions)) {
            throw new NoTransactionException("There is no " + transactionType + " transaction");
        }

        final List<Transaction> transactionWithTypeDiscount = transactionTypeDiscountService.addDiscount(transactions, transactionType);
        final List<Transaction> transactionWithTypeAndDateDiscount = transactionDateDiscountService.addDiscount(transactionWithTypeDiscount);

        return transactionWithPeriodicityAndDateDiscount;
    }

}
