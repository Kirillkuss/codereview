package com.klinik.response;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.klinik.entity.Treatment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseTreatment extends BaseResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Treatment> response;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Treatment treatment;

    public ResponseTreatment(){

    }

    public ResponseTreatment( Integer code, String message ){
        super( code, message );
    }

    public ResponseTreatment( Integer code, String message, List<Treatment> response ){
        super(code, message );
        this.response = response;
    }

    public ResponseTreatment( Integer code, String message, Treatment response ){
        super(code, message );
        this.treatment = response;
    }

    public static ResponseTreatment error( int code, Throwable e ){
        return new ResponseTreatment( code , null == e.getMessage() ? "System malfunction" : e.getMessage());
    }

    @Override
    public String toString() {
        return new StringBuilder(" { \n")
                      .append(response).append(",\n")   
                      .append(treatment).append("\n }\n")
                      .toString();
    }
    
}
