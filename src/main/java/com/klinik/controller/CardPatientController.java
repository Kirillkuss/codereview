package com.klinik.controller;

import com.klinik.entity.Card_patient;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import com.klinik.service.CardPatientService;
import com.klinik.service.PatientService;
import com.klinik.service.TypeComplaintService;
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

@RequestMapping( value = "CardPatient")
@RestController
@Tag(name = "4. Card Patient", description = "Карта пациента")
public class CardPatientController {

    @ExceptionHandler(Throwable.class)
    public BaseResponse errBaseResponse( Throwable ex ){
        return BaseResponse.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public BaseResponse errBaseResponse( MyException ex ){
        return BaseResponse.error( ex.getCode(), ex );
    }

    @Autowired
    private CardPatientService service;

    @Autowired
    private PatientService servicePatient;

    @Autowired
    private TypeComplaintService serviceTypeComplaint;

    @GetMapping(value = "/findCardPatientByDocument")
    @Operation( description = "Поиск карты пациента по документу пациента (СНИЛС, номер документа, ПОЛИС)", summary = "Поиск карты пациента по документу пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Карта пациента найдена по документу пациента", content = { @Content( array = @ArraySchema(schema = @Schema(implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос ",                               content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                               content = { @Content( array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
    public BaseResponse findByDocumentPatient(@Parameter( description = "Параметр поиска:", example = "123243453") String word ) throws Exception, MyException {
        if ( service.findByNumberPolisSnils( word ).getId_card_patient() == null ) throw new MyException( 437, "Карта паицента не найдена");
        return new BaseResponse<>( 200, "success", service.findByNumberPolisSnils( word ));
    }

    @GetMapping(value = "/ByIdCard")
    @Operation( description = "Поиск карты пациента по ИД карты", summary = "Поиск карты пациента по ИД карты")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Карта пациента найдена по ИД карты пациента", content = { @Content( array = @ArraySchema(schema = @Schema(implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                               content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                              content = { @Content( array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
    public BaseResponse getByIdCard( @Parameter( description = "ИД карты пациента", example ="1") Long id ) throws Exception, MyException {
        if( service.findByIdCard( id ) == null ) throw new MyException( 433, "Карты с таким идентификатором карты не существует");
        return new BaseResponse<>( 200, "success", service.findByIdCard( id ));
    }
    
    @GetMapping(value = "/ByIdPatient")
    @Operation( description = "Поиск карты пациента по ИД пациента", summary = "Поиск карты пациента по ИД пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Карта пациента найдена по ИД пациента", content = { @Content( array = @ArraySchema(schema = @Schema(implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                         content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                        content = { @Content( array = @ArraySchema(schema = @Schema( implementation =  BaseResponseError.class ))) })
    })
    public BaseResponse getByIdPatient (@Parameter( description = "ИД Пациента", example = "1") Long id ) throws Exception, MyException {
        if( service.findByPatientId( id ) == null ) throw new MyException( 434, "Карты с таким идентификатором пациента не существует");
        return new BaseResponse<>( 200, "success", service.findByPatientId( id ));
    }

    @PostMapping (value = "/saveCardPatient")
    @Operation( description = "Добавить карту пациента", summary = "Добавить карту пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Карта пациента добавлена", content = { @Content( array = @ArraySchema(schema = @Schema(implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",            content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",           content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public BaseResponse saveCardPatient( Card_patient card_patient, @Parameter( description = "ИД пациента:") Long id_patient) throws Exception, MyException{
        if( service.findByPatientId( id_patient ) != null )                     throw new MyException( 430, "Карта пациента с таким ИД пациента уже существует");
        if( service.findByIdCard( card_patient.getId_card_patient() ) != null ) throw new MyException ( 432, "Карта с таким ИД уже существует");
        card_patient.setPatient( servicePatient.findById( id_patient ));
        return new BaseResponse<>( 200, "success", service.saveCardPatient( card_patient ));
    }

    @PostMapping (value = "/addComplainttoCardPatient")
    @Operation( description = "Добавление жалобы пациенту", summary = "Добавление жалобы пациенту")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Добавление жалобы в карту пациента", content = { @Content( array = @ArraySchema(schema = @Schema(implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                      content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                     content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public BaseResponse saveCardPatient( @Parameter( description = "ИД карты пациента:", example = "1") Long idCard,
                                         @Parameter( description = "ИД Под жалобы:" , example =  "1") Long idComplaint ) throws Exception, MyException{
        if ( service.findByIdCard( idCard ) == null )                                               throw new MyException ( 433, "Карта с таким ИД не существует");
        if ( serviceTypeComplaint.findById( idComplaint ) == null )                                 throw new MyException ( 434, "Под жалобы с таким ИД не существует");
        if ( service.findByIdCardAndIdComplaint(idCard, idComplaint).getId_card_patient() != null ) throw new MyException ( 435, "Под жалоба с таким ИД уже добавлена в карту пацинета");
        service.addCardPatientComplaint( idCard, idComplaint );
        return new BaseResponse<>( 200, "success");
    }

}
