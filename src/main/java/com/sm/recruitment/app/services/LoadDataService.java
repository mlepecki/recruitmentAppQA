package com.sm.recruitment.app.services;

import com.sm.recruitment.app.model.Transaction;

import java.util.List;

import static com.sm.recruitment.app.enums.Periodicity.*;
import static com.sm.recruitment.app.enums.TransactionType.*;
import static java.time.LocalDateTime.now;

/**
 * Below interface stores and load data for demo preview.
 */
public interface LoadDataService {

    String TENANT_A_NAME = "123456";
    String TENANT_B_NAME = "978621";
    
    static List<Transaction> loadDataForDemo() {

        Transaction dailyTransaction = new Transaction(TENANT_A_NAME, 10, CASH, DAILY, now());
        Transaction dailyTransaction2 = new Transaction(TENANT_A_NAME, 10, BANK_TRANSFER, DAILY, now().minusDays(3));
        Transaction weekly = new Transaction(TENANT_A_NAME, 10, BANK_TRANSFER, WEEKLY, now());
        Transaction monthly = new Transaction(TENANT_A_NAME, 100, MOBILE_TRANSFER, MONTHLY, now());


        Transaction dailyTransaction1 = new Transaction(TENANT_B_NAME, 12, CASH, DAILY, now());
        Transaction dailyTransaction12 = new Transaction(TENANT_B_NAME, 16, BANK_TRANSFER, DAILY, now().minusDays(3));
        Transaction weekly1 = new Transaction(TENANT_B_NAME, 12, BANK_TRANSFER, WEEKLY, now());
        Transaction monthly1 = new Transaction(TENANT_B_NAME, 12, MOBILE_TRANSFER, MONTHLY, now());

        return List.of(dailyTransaction, dailyTransaction2,weekly, monthly, dailyTransaction1, dailyTransaction12, weekly1, monthly1);

    };

}
