package com.klinik.service;

import com.klinik.entity.Patient;
import com.klinik.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository repository;

    public List<Patient> getAllPatients() throws Exception{
        return repository.findAll();
    }

    public Patient addPatient( Patient patient ) throws Exception{
        return repository.save( patient );
    }

    public List<Patient> findByWord( String word ) throws Exception{
        return repository.findPatientByWord( word );
    }

    public Patient findById( Long id) throws Exception{
        return repository.findByIdPatinet( id );
    }

    public Patient findByIdDocument( Long id_document ) throws Exception{
        return repository.findPatientByIdDocument(id_document);
    }

    public Patient findByPhone( String phone ) throws Exception{
        return repository.findByPhone(phone);
    }
}
