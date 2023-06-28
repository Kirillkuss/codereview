package com.klinik.response.report;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.klinik.entity.Card_patient;

@Getter
@Setter
public class CardPatinetReport {

    @JsonInclude(Include.NON_NULL)
    private Card_patient card;
    @JsonInclude(Include.NON_NULL)
    private Integer count_treatment;
    @JsonInclude(Include.NON_NULL)
    private List<ResponseReport> treatment;
    
}
