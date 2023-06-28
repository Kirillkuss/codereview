package com.klinik.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Table( name = "Drug_treatment")
@Getter
@Setter
@ToString
public class Drug_treatment {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_drug")
    @Schema( name        = "id_drug",
            description = "ИД медикаментозного лечения",
            example     = "100",
            required    = true )

    private Long id_drug;

    @Column( name = "name")
    @Schema( name        = "name",
            description = "Наименование",
            example     = "Кортикостероиды",
            required    = true )
    private String name;

}
