package com.sm.recruitment.app.services.discount.impl;

import com.sm.recruitment.app.enums.TransactionType;
import com.sm.recruitment.app.exception.NoTransactionAmountException;
import com.sm.recruitment.app.model.Transaction;
import com.sm.recruitment.app.services.discount.TransactionTypeDiscountService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sm.recruitment.app.util.DiscountUtil.addDiscountToSingleTransaction;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;

@Service
@ConfigurationProperties("transaction.multiplier")
@PropertySource("classpath:/transaction.properties")
public class TransactionTypeDiscountServiceImpl implements TransactionTypeDiscountService {

    private final Map<TransactionType, Integer> discountMap = new HashMap<>();

    public Map<TransactionType, Integer> getDiscount() {
        return discountMap;
    }

    @Override
    public List<Transaction> addDiscount(List<Transaction> transactions, TransactionType transactionType) {
        for (Transaction transaction : transactions) {
            if (isNull(transaction.getAmount())) {
                final String formattedString = format("No amount for the transaction: [%s]", transaction.getId());
                throw new NoTransactionAmountException(formattedString);
            }
            final BigDecimal amount = transaction.getAmount();
            if (amount.compareTo(ZERO) > 0) {
                addDiscountToSingleTransaction(discountMap.get(transactionType), transaction);
            }
        }
        return transactions;
    }

}
