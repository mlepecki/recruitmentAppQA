package com.sm.recruitment.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sm.recruitment.app.enums.Periodicity;
import com.sm.recruitment.app.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ResultData {

    @JsonProperty
    private int noOfTransactions;

    @JsonProperty
    private BigDecimal baseCharge;

    @JsonProperty
    private BigDecimal chargeWithDiscount;

    @JsonProperty
    @Nullable
    private TransactionType transactionType;

}
