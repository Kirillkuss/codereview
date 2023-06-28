package com.klinik.entity;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table
@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Record_patient {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id_record")
    @Schema( name        = "id_record",
            description = "ИД записи пациента",
            example     = "100",
            required    = true )
    @JsonInclude(Include.NON_NULL)
    private Long id_record;

    @Column( name = "date_record")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema( name        = "date_record",
            description = "Дата и время записи",
            example     = "2023-01-19T12:00:00.000Z",
            required    = true )
    private LocalDateTime date_record;

    @Column( name = "date_appointment")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema( name        = "date_appointment",
            description = "Дата и время приема",
            example     = "2023-02-01T14:00:00.605Z",
            required    = true )
    private LocalDateTime date_appointment;

    @Column( name = "number_room")
    @Schema( name        = "number_room",
            description = "Номер кабинета",
            example     = "203",
            required    = true )
    private Long number_room;

    @Hidden
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id_doctor")
    private Doctor doctor;

    @Hidden
    @Column( name = "card_patient_id")
    @Schema( name        = "card_patient_id",
            description = "ИД карты",
            example     = "1",
            required    = true )
    @JsonInclude(Include.NON_NULL)
    private Long card_patient_id;

}
