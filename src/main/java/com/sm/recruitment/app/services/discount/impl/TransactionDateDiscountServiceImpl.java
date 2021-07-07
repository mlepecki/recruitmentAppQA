package com.sm.recruitment.app.services.discount.impl;

import com.sm.recruitment.app.exception.NoTransactionDateException;
import com.sm.recruitment.app.model.Transaction;
import com.sm.recruitment.app.services.discount.TransactionDateDiscountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static com.sm.recruitment.app.util.DiscountUtil.addDiscountToSingleTransaction;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.*;

@Service
@Slf4j
public class TransactionDateDiscountServiceImpl implements TransactionDateDiscountService {

    private static final int DISCOUNT_PERCENT_VALUE = 5;

    @Override
    public List<Transaction> addDiscount(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {

            if (Objects.isNull(transaction.getTransactionDate())) {
                throw new NoTransactionDateException("No transaction date!");
            }

            switch (transaction.getPeriodicity()) {
                case DAILY:
                    checkAndAddDateDiscount(transaction, DAYS);
                    log.info("Add daily Date discount");
                    break;
                case WEEKLY:
                    checkAndAddDateDiscount(transaction, WEEKS);
                    log.info("Add weekly Date discount");
                    break;
                case MONTHLY:
                    checkAndAddDateDiscount(transaction, MONTHS);
                    log.info("Add monthly Date discount");
                    break;
                default:
                    log.info("No discount date for transaction");
                    break;
            }
        }
        return transactions;
    }


    private void checkAndAddDateDiscount(Transaction transaction, ChronoUnit unit) {
        final LocalDateTime transactionDate = transaction.getTransactionDate();
        if (transactionDate.plus(1, unit).isAfter(now())
                && transactionDate.isBefore(now())) {
            addDiscountToSingleTransaction(DISCOUNT_PERCENT_VALUE, transaction);
        }
    }

}
