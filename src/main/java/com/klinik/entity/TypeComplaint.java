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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table( name = "Type_complaint")
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class TypeComplaint {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column (name = "id_type_complaint")
    @Schema( name        = "id_type_complaint",
            description = "ИД поджалобы",
            example     = "100",
            required    = true )
    @JsonInclude(Include.NON_NULL)
    private Long id_type_complaint;

    @Column( name = "name")
    @Schema( name        = "name",
            description = "Наименование поджалобы",
            example     = "Парапарезы",
            required    = true )
    @JsonInclude(Include.NON_NULL)
    private String name;


   @Hidden 
   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "complaint_id", referencedColumnName = "id_complaint")
   @JsonInclude(Include.NON_NULL)
   private Сomplaint complaint;

    public TypeComplaint(){

    }

    public TypeComplaint( Long id_type_complaint, String name, Сomplaint complaint ){
        this.id_type_complaint = id_type_complaint;
        this.name = name;
        this.complaint = complaint;
    }
 
}
