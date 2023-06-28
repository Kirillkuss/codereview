package com.klinik.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.persistence.*;

@Entity
@Table(name = "doctor")
@Setter
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
public class Doctor {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_doctor")
    @Schema( name        = "id_doctor",
            description = "ИД доктора",
            example     = "1",
            required    = true )
    @JsonInclude(Include.NON_NULL)
    private Long id_doctor;

    @Column( name = "surname")
    @Schema( name        = "surname",
            description = "Фамилия",
            example     = "Петров",
            required    = true )
    private String surname;

    @Column( name = "name")
    @Schema( name        = "name",
            description = "Имя",
            example     = "Петр",
            required    = true )
    private String name;

    @Column( name = "full_name")
    @Schema( name        = "full_name",
            description = "Отчество",
            example     = "Петрович",
            required    = true )
    private String full_name;

}
