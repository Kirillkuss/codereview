package com.klinik.controller;

import com.klinik.entity.*;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponseError;
import com.klinik.response.ResponseTreatment;
import com.klinik.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping( value = "Treatment")
@RestController
@Tag(name = "7. Treatment", description = "Лечение пациентов:")
public class TreatmentController {

    @ExceptionHandler(Throwable.class)
    public ResponseTreatment errBaseResponse( Throwable ex ){
        return ResponseTreatment.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public ResponseTreatment errBaseResponse( MyException ex ){
        return ResponseTreatment.error( ex.getCode(), ex );
    }

    @Autowired
    private TreatmentService service;

    @Autowired
    private RehabilitationSolutionService rehabilitationSolutionService;
    
    @Autowired 
    private CardPatientService cardPatientService;

    @Autowired 
    private DoctorService doctorService;

    @Autowired
    private DrugService serviceDrug;

    //@GetMapping(value = "/getAllTreatment")
    @Operation( description = "Получение списка всех лечений", summary = "Получение списка всех лечений")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен список лечений", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = ResponseTreatment.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",          content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",         content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public ResponseTreatment getAllTreatment() throws Exception{
        return new ResponseTreatment( 200, "успешно", service.allListTreatment());
    }

    @PostMapping( value = "/addTreatment")
    @Operation( description = "Добавить лечение для пациента", summary = "Добавить лечение для пациента")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Добавлено новое лечение паиценту", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = ResponseTreatment.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                    content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                   content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public ResponseTreatment addTreatment(  Treatment treatment,
                                            @Parameter( description = "ИД медикаментозного лечения (Препарата):",  example = "1") Long drug_id,
                                            @Parameter( description = "Ид карты пациента:",                        example = "1") Long card_patient_id,
                                            @Parameter( description = "Ид реабилитационного лечения:",             example = "1") Long rehabilitation_solution_id,
                                            @Parameter( description = "Ид доктор:",                                example = "1") Long doctor_id ) throws Exception{
        if( serviceDrug.findById( drug_id) == null )                                          throw new MyException( 474, "Указано неверное значение медикаментозного лечения, укажите другой");
        if( service.findById( treatment.getId_treatment()) != null  )                         throw new MyException( 470, "Лечение с таким ИД уже существует, используйте другой");
        if( rehabilitationSolutionService.findByIdList(rehabilitation_solution_id) == null  ) throw new MyException( 471, "Указано неверное значение реабилитационного лечения, укажите другой");
        if( cardPatientService.findByIdCard(card_patient_id ) == null )                       throw new MyException( 472, "Указано неверное значение карты пациента, укажите другой");
        if( doctorService.findById( doctor_id )  == null )                                    throw new MyException( 473, "Указано неверное значение ид доктора, укажите другой");
        treatment.setCard_patient_id( cardPatientService.findByIdCard(card_patient_id ).getId_card_patient() );
        treatment.setRehabilitation_solution( rehabilitationSolutionService.findByIdList(rehabilitation_solution_id) );
        treatment.setDoctor( doctorService.findById( doctor_id ) );
        treatment.setDrug( serviceDrug.findById( drug_id ));
        return new ResponseTreatment( 200, "success", service.addTreatment( treatment ));              
    }

    @GetMapping(value = "/findByParamIdCardAndDateStart")
    @Operation( description = "Получение списка лечений по параметрам", summary = "Получение списка лечений по параметрам")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен список лечений по параметрам", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = ResponseTreatment.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public ResponseTreatment findByParamIdCardAndDateStart( @Parameter( description = "Ид карты",                example = "1") Long id,
                                                 @Parameter( description = "Время начала лечения с:", example = "2023-01-20T12:47:07.605") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                                 @Parameter( description = "Время начала лечения по", example = "2023-09-20T12:47:07.605") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo) throws Exception{
        return new ResponseTreatment( 200, "success", service.findByParamIdCardAndDateStart(id, dateFrom, dateTo));
    }

    @GetMapping(value = "/findByParamIdCardAndIdRh")
    @Operation( description = "Получение списка лечений по параметрам", summary = "Получение списка лечений по параметрам")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен список лечений по параметрам", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = ResponseTreatment.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public ResponseTreatment findByParamIdCardAndIdRh( @Parameter( description = "Ид карты пациента",            example = "1") Long idCard, 
                                                       @Parameter( description = "Ид реабилитационного лечения", example = "1") Long idReSol ) throws Exception{
        return new ResponseTreatment( 200, "success", service.findByParamIdCardAndIdRh( idCard, idReSol ));
    }

}
