package com.klinik.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

//Сущночть лечение

@Entity
@Table( name = "Treatment")
@Setter
@Getter
@EqualsAndHashCode
public class Treatment {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_treatment")
    @Schema( name        = "id_treatment",
            description = "ИД лечения",
            example     = "100",
            required    = true )
    private Long id_treatment;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column( name = "time_start_treatment")
    @Schema( name        = "time_start_treatment",
            description = "Дата начала лечения",
            example     = "2023-01-22 18:00:00.745",
            required    = true )
    private LocalDateTime time_start_treatment;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column( name = "end_time_treatment")
    @Schema( name        = "end_time_treatment",
            description = "Дата окончания лечения",
            example     = "2023-07-22 18:00:00.745",
            required    = true )
    private LocalDateTime end_time_treatment;

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "drug_id", referencedColumnName = "id_dr")
    private  Drug drug;

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rehabilitation_solution_id", referencedColumnName = "id_rehabilitation_solution")
    private Rehabilitation_solution rehabilitation_solution;

    @Hidden
    @Column( name = "card_patient_id")
    @Schema( name        = "card_patient_id",
            description = "ИД карты пациента",
            example     = "1",
            required    = true )
    private Long card_patient_id;

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id_doctor")
    private Doctor doctor;   

    @Override
    public String toString() {
        return new StringBuilder("\n{ \n")
                      .append("   Ид лечения: ").append(id_treatment).append(",\n")  
                      .append("   Дата начала лечения: ").append(time_start_treatment).append(",\n")
                      .append("   Дата окончания лечения: ").append(end_time_treatment).append(",\n")
                      .append("   Медикаментозное лечение: ").append(drug).append("   ,\n")
                      .append("   Реабилитационное лечение: ").append(rehabilitation_solution).append("   ,\n")
                      .append("   Ид карты пациента: ").append(card_patient_id).append(",\n")
                      .append(doctor).append("\n }\n")
                      .toString();
    }

}
