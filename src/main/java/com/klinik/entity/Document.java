package com.klinik.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

@Entity
@Table( name = "document")
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Document {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_document")
    @Schema( name        = "id_document",
            description = "ИД документа",
            example     = "100",
            required    = true )
    private Long id_document;

    @Column( name = "type_document")
    @Schema( name        = "type_document",
            description = "Тип документа",
            example     = "Паспорт",
            required    = true )
    private String type_document;

    @Column( name = "seria")
    @Schema( name        = "seria",
            description = "Серия документа",
            example     = "ВМ",
            required    = true )
    private String seria;

    @Column( name = "numar")
    @Schema( name        = "numar",
            description = "Номер документа",
            example     = "123243455",
            required    = true )
    private String numar;

    @Column( name = "snils")
    @Schema( name        = "snils",
            description = "СНИЛС",
            example     = "123-456-789-01",
            required    = true )
    private String snils;

    @Column( name = "polis")
    @Schema( name        = "polis",
            description = "Полис",
            example     = "0000 0000 0000 0000",
            required    = true )
    private String polis;

}
