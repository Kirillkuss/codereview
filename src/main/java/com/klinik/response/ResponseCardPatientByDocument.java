package com.klinik.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.klinik.entity.Card_patient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
public class ResponseCardPatientByDocument extends BaseResponse{



    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Card_patient cardPatient;

    public ResponseCardPatientByDocument(){

    }

    public ResponseCardPatientByDocument( Integer code, String message ){
        super( code, message );
    }

    public ResponseCardPatientByDocument( Integer code, String message, Card_patient cardPatient ){
        super( code, message);
        this.cardPatient = cardPatient;
    }

    public static ResponseCardPatientByDocument error( int code, Throwable e ){
        return new ResponseCardPatientByDocument( code , null == e.getMessage() ? "System malfunction" : e.getMessage());
    }

    @Override
    public String toString() {
        return new StringBuilder(" {")
                      .append(cardPatient == null ? "" : cardPatient ).append(" }")
                      .toString();
    }
}
