package com.klinik.repositories;

import com.klinik.entity.Treatment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    @Query("SELECT u FROM Treatment u WHERE u.card_patient_id = :id and (( u.time_start_treatment >= :from)  and (  u.time_start_treatment <= :to))" )
    List<Treatment> findByParamIdCardAndDateStart( Long id, LocalDateTime from, LocalDateTime to) throws Exception;

    
    @Query("SELECT u FROM Treatment u WHERE u.card_patient_id = :idCard and u.rehabilitation_solution.id_rehabilitation_solution = :idReSol" )
    List<Treatment> findByParamIdCardAndIdRh( Long idCard, Long idReSol) throws Exception;

    @Query( "SELECT u FROM Treatment u WHERE u.id_treatment = :id")
    Treatment findByIdTreatment( Long id ) throws Exception;
}
