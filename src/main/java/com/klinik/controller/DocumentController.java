package com.klinik.controller;

import com.klinik.entity.Document;
import com.klinik.excep.MyException;
import com.klinik.response.BaseResponse;
import com.klinik.response.BaseResponseError;
import com.klinik.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping( value = "Documents")
@RestController
@Tag(name = "3. Documents", description = "Документ пациента")
public class DocumentController {

    @ExceptionHandler(Throwable.class)
    public BaseResponse errBaseResponse( Throwable ex ){
        return BaseResponse.error( 999, ex );
    }

    @ExceptionHandler(MyException.class)
    public BaseResponse errBaseResponse( MyException ex ){
        return BaseResponse.error( ex.getCode(), ex );
    }

    @Autowired
    DocumentService service;

    @GetMapping(value = "/getAllDocunets")
    @Operation( description = "Список всех документов", summary = "Список всех документов")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Получение списка документов", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",               content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",              content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    public BaseResponse getAllDocuments() throws Exception, MyException{
        return new BaseResponse<>( 200, "success", service.getAllDocuments() );
    }

    @Operation( description = "Добавить документ", summary = "Добавить документ")
    @ApiResponses(value = {
            @ApiResponse( responseCode = "200", description = "Документ добавлен", content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponse.class))) }),
            @ApiResponse( responseCode = "400", description = "Плохой запрос",     content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
            @ApiResponse( responseCode = "500", description = "Ошибка сервера",    content = { @Content( array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
    })
    @RequestMapping( method = RequestMethod.POST , value = "/addDocument")
    public BaseResponse addDocument( Document document ) throws Exception, MyException{
        if ( service.findById( document.getId_document()) != null ) throw new MyException( 410, "Документ с таким ИД документа уже существует, используйте другой ИД");
        if ( service.findByNumar( document.getNumar()) != null )    throw new MyException( 411, "Документ с таким номером документа уже существует");
        if ( service.findByPolis( document.getPolis()) != null )    throw new MyException( 412, "Документ с таким полисом уже существует");
        if ( service.findBySnils( document.getSnils()) != null )    throw new MyException( 413, "Документ с таким СНИЛСом уже существует");
        return new BaseResponse<>( 200, "success", service.addDocument( document ));
    }

}
