package com.sm.recruitment.app.services.impl;

import com.sm.recruitment.app.enums.TransactionType;
import com.sm.recruitment.app.model.Result;
import com.sm.recruitment.app.model.ResultData;
import com.sm.recruitment.app.model.Transaction;
import com.sm.recruitment.app.services.CalculateResult;
import com.sm.recruitment.app.services.MainDiscountsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sm.recruitment.app.enums.TransactionType.ALL;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.groupingBy;

@Service
public class CalculateResultImpl implements CalculateResult {

    private final MainDiscountsService mainDiscountsService;

    public CalculateResultImpl(MainDiscountsService mainDiscountsService) {
        this.mainDiscountsService = mainDiscountsService;
    }

    @Override
    public List<Result> prepareResult(List<Transaction> transactions) {

        final Map<String, List<Transaction>> clientToTransaction = getTenantIdToTransactions(transactions);

        List<Result> finalResults = new ArrayList<>();

        final Map<String, Map<TransactionType, List<Transaction>>> tenantIdToTransactionTypeToTransactions = getTenantIdToTransactionTypeToTransactions(clientToTransaction);

        tenantIdToTransactionTypeToTransactions.forEach( (tenantID, transactionsToTransactionType) -> {
            List<ResultData> tenantResults = new ArrayList<>();
            transactionsToTransactionType.forEach((transactionType, transactionsPerType) -> {
                final BigDecimal baseAmount = getAmountOfSpecificTransactionsType(transactionsPerType);
                final List<Transaction> dailyTransactions = mainDiscountsService.calculateTransactions(transactionsPerType, transactionType);
                addTenantResultToFinalResponse(tenantResults, dailyTransactions, baseAmount, transactionType);
            });
            calculateSummaryForTenant(tenantResults);
            finalResults.add(Result.builder().tenantId(tenantID).listOfResults(tenantResults).build());
        });
        return finalResults;
    }

    private void calculateSummaryForTenant(List<ResultData> listOfResults) {
        listOfResults.add(new ResultData(
                listOfResults.stream().mapToInt(ResultData::getNoOfTransactions).sum(),
                listOfResults.stream().map(ResultData::getBaseCharge).reduce(BigDecimal.ZERO, BigDecimal::add),
                listOfResults.stream().map(ResultData::getChargeWithDiscount).reduce(BigDecimal.ZERO, BigDecimal::add),
                ALL
        ));
    }

    private Map<String, List<Transaction>> getTenantIdToTransactions(List<Transaction> transactions) {
        return transactions
                .stream()
                .collect(groupingBy(Transaction::getTenantId));
    }

    private Map<String, Map<TransactionType, List<Transaction>>> getTenantIdToTransactionTypeToTransactions(Map<String, List<Transaction>> clientToTransaction) {
        return clientToTransaction.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream().collect(groupingBy(Transaction::getType))));
    }

    private void addTenantResultToFinalResponse(List<ResultData> tenantResults,
                                                List<Transaction> transactions,
                                                BigDecimal baseAmount,
                                                TransactionType transactionType) {
        tenantResults.add(new ResultData(
                getNoOfTransactions(transactions),
                baseAmount,
                getAmountOfSpecificTransactionsType(transactions), transactionType));
    }

    private BigDecimal getAmountOfSpecificTransactionsType(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(ZERO, BigDecimal::add);
    }

    private int getNoOfTransactions(List<Transaction> transactions) {
        return transactions.size();
    }

}
