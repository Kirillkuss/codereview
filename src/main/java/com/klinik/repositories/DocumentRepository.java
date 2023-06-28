package com.klinik.repositories;

import com.klinik.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {

    @Query( "SELECT u FROM Document u WHERE u.numar = :numar")
    Document findByNumar( String numar);

    @Query( "SELECT u FROM Document u WHERE u.snils = :snils")
    Document findBySnils( String snils );
    
    @Query( "SELECT u FROM Document u WHERE u.polis = :polis")
    Document findByPolis( String polis );

    @Query("select e from Document e where e.id = :id")
    Document findByIdDocument( Long id );
    
}
