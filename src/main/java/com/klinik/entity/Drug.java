package com.klinik.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table( name = "drug")
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Drug {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_dr")
    @Schema( name        = "id_dr",
            description = "ИД лекарства",
            example     = "100",
            required    = true )
    private Long id_dr;

    @Column( name = "name")
    @Schema( name        = "name",
            description = "Препараты",
            example     = "Карвалол 2 чайные ложки в день",
            required    = true )
    private String name;

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "drug_id", referencedColumnName = "id_drug")
    private Drug_treatment drugTreatment ;

    public Drug(){

    }

    public Drug( Long id_dr, String name, Drug_treatment drugTreatment){
        this.id_dr = id_dr;
        this.name = name;
        this.drugTreatment = drugTreatment;
    }

    @Override
    public String toString() {
        return new StringBuilder("  Препарат { \n")
                      .append("  1. Ид препарата: ").append(id_dr).append(",\n")
                      .append("  2. Наименование: ").append(name).append(",\n")  
                      .append("  3. Мед. лечения:").append(drugTreatment).append("\n  }\n")
                      .toString();
    }
    
}
