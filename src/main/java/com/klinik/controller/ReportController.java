package com.klinik.controller;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import com.klinik.response.report.RecordPatientReport;
import com.klinik.service.report.ReportService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/Reports")
@RestController
@Tag(name = "Report", description = "Отчеты:")
public class ReportController {

    @ExceptionHandler(Throwable.class)
    public BaseResponse errBaseResponse( Throwable ex ){
        return BaseResponse.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public BaseResponse errBaseResponse( MyException ex ){
        return BaseResponse.error( ex.getCode(), ex );
    }

    @Autowired
    private ReportService service;

    @Operation( description = "Отчет по виду ребилитационного лечения за период времени", summary = "Отчет по виду ребилитационного лечения за период времени")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен отчет по виду ребилитационного лечения за период времени", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                                                    content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                                                   content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) })
    })
    @GetMapping("/report_rehabilitation_treatment_for_time")
    public BaseResponse report( @Parameter( description = "Дата начала выборки:", example = "2021-05-24T14:02:35.584")   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                @Parameter( description = "Дата конца выборки:", example = "2023-12-24T14:02:35.584")   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo ) throws Exception{
        return new BaseResponse<>( 200, "success", service.getStatReport( dateFrom, dateTo));
    }


    @Operation( description = "Отчет о полной информации по пациенту", summary = "Отчет о полной информации по пациенту")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен отчет о полной информации по пациенту", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                                 content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                                content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    @GetMapping("/report_full_info_patient")
    public BaseResponse fullInformationPatient(  @Parameter( description = "Ид карты пациента:", example = "1")  Long idCard ) throws Exception{ 
        return new BaseResponse<>( 200, "success", service.reportInformationAboutPatient( idCard ));
    }

    @Operation( description = "Отчет по записям пациента к врачу за период времени", summary = "Отчет по записям пациента к врачу за период времени")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен отчет по записям пациента к врачу за период времени", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = RecordPatientReport.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                                               content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                                              content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    @GetMapping("/report_info_report_patient")
    public BaseResponse findInformationAboutRecordPatient( @Parameter( description = "ИД пациента:",         example = "1") Long IdPatient,
                                                           @Parameter( description = "Дата начала выборки:", example = "2023-01-24T14:02:35.584") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                                           @Parameter( description = "Дата начала выборки:", example = "2023-12-24T14:02:35.584") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo ) throws Exception{
        return new BaseResponse<>( 200, "успешно", service.reportByPatietnWithRecordPatient( IdPatient, dateFrom, dateTo ));
    }


    @Operation( description = "Отчет о медикаментозном лечении за период времени", summary = "Отчет о медикаментозном лечении за период времени")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен отчет о медикаментозном лечении за период времени", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                                             content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                                            content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    @GetMapping( "/report_drug_treatment")
    public BaseResponse getReportDrug(@Parameter( description = "Дата начала выборки:", example = "2023-01-24T14:02:35.584") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                      @Parameter( description = "Дата начала выборки:", example = "2023-12-24T14:02:35.584") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo ) throws Exception{
        return new BaseResponse<>(200, "успешно", service.reportStatDrug( dateFrom, dateTo ));
    }
    
}
