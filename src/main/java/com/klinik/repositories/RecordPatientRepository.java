package com.klinik.repositories;

import com.klinik.entity.Record_patient;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordPatientRepository extends JpaRepository<Record_patient,Long> {

    @Query("SELECT u FROM Record_patient u WHERE u.card_patient_id = :id and (( u.date_record >= :fromLDT)  and (  u.date_record <= :toLDT))" )
    List<Record_patient> findByParamTwo( Long id, LocalDateTime fromLDT, LocalDateTime toLDT) throws Exception;

    @Query( "SELECT e FROM Record_patient e WHERE e.id_record = :id")
    Record_patient findByIdRecordPatient( Long id) throws Exception;
}
