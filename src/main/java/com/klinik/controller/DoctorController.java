package com.klinik.controller;

import com.klinik.entity.Doctor;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import com.klinik.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping( value = "Doctors")
@RestController
@Tag(name = "1. Doctors", description = "Доктора:")
public class DoctorController {

    @ExceptionHandler(Throwable.class)
    public BaseResponse errBaseResponse( Throwable ex ){
        return BaseResponse.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public BaseResponse errBaseResponse( MyException ex ){
        return BaseResponse.error( ex.getCode(), ex );
    }

    @Autowired
    DoctorService service;

    @GetMapping(value = "/getAll")
    @Operation( description = "Список всех докторов", summary = "Список всех докторов")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Список всех докторов", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public BaseResponse  getAllDoc() throws Exception{
        return new BaseResponse<>( 200, "success", service.allDoctor() );
    }

    @GetMapping(value = "/FindDoctorByFIO")
    @Operation( description = "Поиск врача по ФИО", summary = "Поиск врача по ФИО")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Поиск врача по ФИО", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",      content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера",     content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public BaseResponse findByFIO(@Parameter( description = "ФИО врача") String word ) throws Exception{
        if( service.findByFIO( word ).isEmpty() == true ) throw new MyException( 417, "По данному запросу ничего не найдено");
        return new BaseResponse<>( 200, "success", service.findByFIO( word )); 
    }

    @PostMapping( value = "/addDoctor")
    @Operation( description = "Добавить доктора", summary = "Добавить доктора")
    @ApiResponses(value = {
        @ApiResponse( responseCode = "200", description = "Добавить доктора", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
        @ApiResponse( responseCode = "400", description = "Плохой запрос",    content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
        @ApiResponse( responseCode = "500", description = "Ошибка сервера",   content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public BaseResponse addDoctor( Doctor doctor ) throws Exception{
        if (service.findById( doctor.getId_doctor() ) != null ) throw new MyException( 418, "Пользователь с таким ИД уще существует");
        return new BaseResponse<>( 200, "success", service.saveDoctor( doctor ));
    }
}
