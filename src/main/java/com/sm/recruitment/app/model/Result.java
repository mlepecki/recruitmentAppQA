package com.sm.recruitment.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Result {

    @JsonProperty
    private String tenantId;

    @JsonProperty("_data")
    private List<ResultData> listOfResults;

}
