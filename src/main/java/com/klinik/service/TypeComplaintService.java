package com.klinik.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klinik.entity.TypeComplaint;
import com.klinik.repositories.TypeComplaintRepository;

@Service
public class TypeComplaintService {
    
    @Autowired
    private TypeComplaintRepository repository;

    public List<TypeComplaint> findByAll() throws Exception{
        return repository.findAll();
    }

    public TypeComplaint findById( Long id ) throws Exception{
        return repository.findById( id ).stream().findFirst().orElse(null);
    }

    public TypeComplaint saveTypeComplaint( TypeComplaint complaint ) throws Exception{
        return repository.save( complaint );
    }

    public TypeComplaint findByNme( String name ) throws Exception{
        return repository.findName( name );
    }

     public List<TypeComplaint> findByIdComplaint( Long id ) throws Exception{
        return repository.findByIdComplaint( id );
    }
}
