package com.klinik.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.entity.TypeComplaint;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import com.klinik.service.ComplaintService;
import com.klinik.service.TypeComplaintService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping( value = "Сomplaint")
@RestController
@Tag(name = "6. Сomplaint", description = "Справочник: Жалобы и под жалобы ")
public class СomplaintController {
    
    @ExceptionHandler(Throwable.class)
    public BaseResponse errBaseResponse( Throwable ex ){
        return BaseResponse.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public BaseResponse errBaseResponse( MyException ex ){
        return BaseResponse.error( ex.getCode(), ex );
    }

    @Autowired
    private ComplaintService service;

    @Autowired
    private TypeComplaintService serviceTC;

    @GetMapping(value = "/getAll")
    @Operation( description = "Получение справочника жалобы", summary = "Получение справочника жалобы")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получен список жалобы", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",         content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",        content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    public BaseResponse findAll() throws Exception{
        return new BaseResponse<>( 200, "suceess", service.listComplaints());
    }

    @Operation( description = "Добавление справочника жалобы", summary = "Добавление справочника жалобы")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Добавлена жалоба", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",    content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",   content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    @PostMapping( value = "/saveСomplaint")
    public BaseResponse saveСomplaint( com.klinik.entity.Сomplaint сomplaint ) throws Exception{
        if( service.findById( сomplaint.getId_complaint() ) !=null )            throw new MyException( 460, "Справочник жалоба с таким ИЖ уже существует");
        if( service.findByName( сomplaint.getFunctional_impairment() ) !=null ) throw new MyException( 461, "Справочник жалоба с таким наименованием уже существует");
        return new BaseResponse<>( 200, "success", service.saveСomplaint( сomplaint ));
    }

    @Operation( description = "Добавление под жалобы", summary = "Добавление под жалобы")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Добавлена под жалоба", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",       content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",      content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    @PostMapping( value = "/TypeComplaint")
    public BaseResponse saveTypeComplaint(TypeComplaint request, @Parameter( description = "ИД жалобы", example = "1") Long idComplaint ) throws Exception{
        if( service.findById( idComplaint) == null )                      throw new MyException( 462, "Жалобы с таким ИД не существует");
        if( serviceTC.findByNme( request.getName()) != null)              throw new MyException( 463, "Под жалоба с таким наименование уже существует");
        if( serviceTC.findById( request.getId_type_complaint()) != null ) throw new MyException( 464, "Под жалоба с таким ИД уже существует");
        request.setComplaint( service.findById( idComplaint ));
        return new BaseResponse<>(200, "success", serviceTC.saveTypeComplaint( request ));
    }

    @Operation( description = "Получение жалобы с под жалобами", summary = "Получение жалобы с под жалобами")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получение жалобы с под жалобами", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",                   content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",                  content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class))) })
    })
    @GetMapping( "/findComplaintWithTypeComplaints")
    public BaseResponse listComplaintWithTypeComplaints( @Parameter( description = "Ид жалобы", example = "1" )Long Id ) throws Exception{
        if( service.findById( Id ) == null ) throw new MyException( 462, "Жалобы с таким ИД не существует");
        return new BaseResponse<>( 200, "success", serviceTC.findByIdComplaint( Id ));
    }
 
}
