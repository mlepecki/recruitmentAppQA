package com.sm.recruitment.app.model;

import com.sm.recruitment.app.enums.Periodicity;
import com.sm.recruitment.app.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private String id;
    private String tenantId;
    private BigDecimal amount;
    private TransactionType type;
    private Periodicity periodicity;
    private LocalDateTime transactionDate;

    public Transaction(String tenantId, int intAmount, TransactionType type, Periodicity periodicity, LocalDateTime transactionDate) {
        this.id = UUID.randomUUID().toString();
        this.tenantId = tenantId;
        this.amount = BigDecimal.valueOf(intAmount);
        this.type = type;
        this.periodicity = periodicity;
        this.transactionDate = transactionDate;
    }

}
