package com.klinik.repositories;

import com.klinik.entity.Rehabilitation_solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RehabilitationSolutionRepository extends JpaRepository<Rehabilitation_solution, Long> {

    @Query( "SELECT u FROM Rehabilitation_solution u WHERE u.name = :name")
    Rehabilitation_solution findByName( String name );

    @Query( "SELECT u FROM Rehabilitation_solution u WHERE u.id_rehabilitation_solution = :idList")
    Rehabilitation_solution findByIdList(Long idList );

}
