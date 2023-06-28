package com.klinik.repositories;

import com.klinik.entity.Drug_treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugTreatmentRepository extends JpaRepository<Drug_treatment, Long> {

    @Query( "SELECT u FROM Drug_treatment u WHERE u.name = :name")
    Drug_treatment findByName( String name );
}
