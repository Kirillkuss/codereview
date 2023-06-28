package com.klinik.controller;

import com.klinik.entity.Patient;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import com.klinik.service.DocumentService;
import com.klinik.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping( value = "Patients")
@RestController
@Tag(name = "2. Patient", description = "Пациенты:")
public class PatientController {

    @ExceptionHandler(Throwable.class)
    public BaseResponse errBaseResponse( Throwable ex ){
        return BaseResponse.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public BaseResponse errBaseResponse( MyException ex ){
        return BaseResponse.error( ex.getCode(), ex );
    }

    @Autowired
    private PatientService service;

    @Autowired DocumentService docService;

    @GetMapping(value = "/getAllPatients")
    @Operation( description = "Список всех пациентов", summary = "Список всех пациентов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен список всех пациентов", content = { @Content(array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class ))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                 content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public BaseResponse getAllPatients() throws Exception, MyException{
        return new BaseResponse<>( 200, "success", service.getAllPatients());
    }

    @PostMapping( value = "/addPatient")
    @Operation( description = "Добавить пациента", summary = "Добавить пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Пациент добавлен", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class ))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",    content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",   content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public BaseResponse addPatient(Patient patient,  @Parameter( description = "Ид документа" , example = "1") Long id) throws Exception, MyException{
        if( service.findByPhone( patient.getPhone() ) != null )  throw new MyException( 423, "Пользователь с таким номером телефона уже существует, укажите другой");
        if( service.findByIdDocument( id ) != null )             throw new MyException( 420, "Не верное значение ИД документа, попробуйте другой");
        if( service.findById( patient.getId_patient()) != null ) throw new MyException( 421, "Пользователь с таким ИД уже существует");
        if( docService.findById( id ) == null)                   throw new MyException( 422, "Документ с таким ИД не существует");
        patient.setDocument(  docService.findById( id ) );
        return new BaseResponse<>( 200, "success", service.addPatient( patient ));
    }

    @RequestMapping( method = RequestMethod.GET, value = "/findByWord")
    @Operation( description = "Поиск пациента по ФИО или номеру телефона", summary = "Поиск пациента по ФИО или номеру телефона")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Пациент найден", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class ))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",  content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public BaseResponse findByWord( @Parameter( description = "Параметр поиска")  String word ) throws Exception, MyException{
        if ( service.findByWord( word ).isEmpty() == true ) throw new MyException( 999, "По данному запросу ничего не найдено");
        return new BaseResponse<>( 200, "success",service.findByWord( word ));
    }

 }
