package com.klinik.excep;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MyException extends Exception{

    private int code = 999;

    public MyException(){
    }

    public MyException( int code, String message ){
        super( message );
        this.code = code;
    }

    
}
