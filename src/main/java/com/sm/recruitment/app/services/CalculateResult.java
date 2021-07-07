package com.sm.recruitment.app.services;

import com.sm.recruitment.app.model.Result;
import com.sm.recruitment.app.model.Transaction;

import java.util.List;

/**
 * Below processor calculate all the transactions and prepare final
 * result and send back to the client in JSON format
 */
public interface CalculateResult {

    List<Result> prepareResult(List<Transaction> transactions);

}
