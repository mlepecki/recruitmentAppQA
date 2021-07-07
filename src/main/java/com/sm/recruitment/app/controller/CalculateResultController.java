package com.sm.recruitment.app.controller;

import com.sm.recruitment.app.model.Result;
import com.sm.recruitment.app.services.CalculateResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.sm.recruitment.app.services.LoadDataService.loadDataForDemo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class CalculateResultController {

    private final CalculateResult calculateResult;

    public CalculateResultController(CalculateResult calculateResult) {
        this.calculateResult = calculateResult;
    }

    @GetMapping(value = "/consumeTransaction", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Result>> consumeTransactions () {
        return ok(calculateResult.prepareResult(loadDataForDemo()));
    }

}
