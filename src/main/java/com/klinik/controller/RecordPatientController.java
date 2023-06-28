package com.klinik.controller;

import com.klinik.entity.Record_patient;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import com.klinik.service.CardPatientService;
import com.klinik.service.DoctorService;
import com.klinik.service.RecordPatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RequestMapping( value = "RecordsPatients")
@RestController
@Tag(name = "5. Records Patients", description = "Записи пациентов:")
public class RecordPatientController {

    @ExceptionHandler(Throwable.class)
    public BaseResponse errBaseResponse( Throwable ex ){
        return BaseResponse.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public BaseResponse errBaseResponse( MyException ex ){
        return BaseResponse.error( ex.getCode(), ex );
    }

    @Autowired
    private RecordPatientService service;

    @Autowired
    private DoctorService serviceDoctor;

    @Autowired
    private CardPatientService servicePatientCard;

    //@GetMapping(value = "/getAllRecordPatient")
    @Operation( description = "Список всех записей пациентов к врачу", summary = "Список всех записей пациентов к врачу")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Список всех записей пациентов к врачу", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                         content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public BaseResponse allListRecordPatient() throws Exception, MyException{
        return new BaseResponse<>( 200, "success", service.allListRecordPatient() );
    }

    @PostMapping (value = "/addRecordPatient")
    @Operation( description = "Добавить запись пациента к врачу", summary = "Добавить запись пациента к врачу")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Запись к врачу добавлена", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",            content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",           content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public BaseResponse addRecordPatient( Record_patient record_patient,
                                          @Parameter( description = "Ид доктора") Long doctor_id,
                                          @Parameter( description = "Ид карты пациента") Long card_patient_id) throws Exception, MyException{
        if ( serviceDoctor.findById( doctor_id ) == null )                throw new MyException( 440, "Нет доктора с таким идентификатором");
        if ( servicePatientCard.findByIdCard( card_patient_id ) == null ) throw new MyException( 441, "Нет карты пациента с таким идентификатором");
        if ( service.findById( record_patient.getId_record()) != null)    throw new MyException( 442, "Запись к врачу с таким ИД уже существует, установите другой ИД записи к врачу");
        record_patient.setDoctor(serviceDoctor.findById( doctor_id ));;
        record_patient.setCard_patient_id( card_patient_id );
        return new BaseResponse<>( 200, "success", service.saveRecordPatient( record_patient));                
    }


    @GetMapping(value = "/findByParams")
    @Operation( description = "Список всех записей пациентов к врачу по параметрам", summary = "Список всех записей пациентов к врачу по параметрам ")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен список записей к врачу", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                  content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                 content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public BaseResponse findByParams( @Parameter(description = "ИД карты пациента", example = "1") Long id,
                                      @Parameter(description = "Дата записи с:", example = "2023-02-19T12:47:07.605")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                      @Parameter(description = "Дата записи по:", example = "2023-05-19T12:47:07.605") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo ) throws Exception, MyException{
        return new BaseResponse<>( 200, "success", service.findByParam(id, dateFrom, dateTo));
    }

    
}
