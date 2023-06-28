package com.klinik.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;

//Сущность жалоба
@Entity
@Table( name = "complaint")
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Сomplaint {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_complaint")
    @Schema( name        = "id_complaint",
            description = "ИД жалобы",
            example     = "100",
            required    = true )
    @JsonInclude(Include.NON_NULL)        
    private Long id_complaint;

    @Column( name = "functional_impairment")
    @Schema( name        = "functional_impairment",
            description = "Наименование жалобы",
            example     = "Симптомы поражения пирамидного тракта",
            required    = true )
    private String functional_impairment;

    public Сomplaint(){

    }

    public Сomplaint( Long id_complaint, String functional_impairment ){
        this.id_complaint = id_complaint;
        this.functional_impairment = functional_impairment;
    }

    public Сomplaint( String functional_impairment ){
        this.functional_impairment = functional_impairment;  
    }

}
