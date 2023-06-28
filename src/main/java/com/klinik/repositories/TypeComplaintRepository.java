package com.klinik.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.klinik.entity.TypeComplaint;

@Repository
public interface TypeComplaintRepository extends JpaRepository<TypeComplaint, Long> {
    
    @Query( "SELECT u from TypeComplaint u WHERE u.name = :name")
    TypeComplaint findName( String name);

    @Query( "SELECT u FROM  TypeComplaint u WHERE u.complaint.id_complaint = :id")
    List<TypeComplaint> findByIdComplaint( Long id );

    
}
