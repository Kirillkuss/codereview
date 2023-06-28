package com.klinik.service;

import com.klinik.entity.Card_patient;
import com.klinik.entity.Document;
import com.klinik.entity.Gender;
import com.klinik.entity.Patient;
import com.klinik.entity.TypeComplaint;
import com.klinik.entity.Сomplaint;
import com.klinik.repositories.CardPatientRepository;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Service
public class CardPatientService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private CardPatientRepository repository;

    public List<Card_patient> allListCardPatient() throws Exception{
        return repository.findAll();
    }

    public Card_patient saveCardPatient( Card_patient card_patient ) throws Exception{
        return repository.save( card_patient );
    }

    public Card_patient findByPatientId( Long id ) throws Exception{
        return repository.findByPatientId( id );
    }

    public Card_patient findByIdCard( Long id ) throws Exception{
        return repository.findByIdCard(id);
    }

    @Transactional
    public void addCardPatientComplaint( Long IdCard, Long IdComplaint ) throws Exception{
        em.createNativeQuery( "INSERT INTO Card_patient_Complaint(card_patient_id, type_complaint_id) VALUES (?,?)")
                .setParameter(1, IdCard)
                .setParameter( 2, IdComplaint)
                .executeUpdate();
    }

    public Card_patient findByIdCardAndIdComplaint( Long idCard, Long IdComplaint ) throws Exception{
        String sql = "SELECT u.id_card_patient FROM Card_patient u "
                    + " left join Card_patient_Complaint cpc on cpc.card_patient_id = u.id_card_patient "
                    + " left join Type_complaint c on c.id_type_complaint = cpc.type_complaint_id "
                    +" WHERE u.id_card_patient = ? and c.id_type_complaint = ? ";
        Session session;
        Card_patient card = new Card_patient();
        try{
            session = em.unwrap( Session.class);
            session.doWork( (Connection conn ) ->{
                try( PreparedStatement st = conn.prepareStatement( sql )){
                    st.setLong(1, idCard);
                    st.setLong( 2, IdComplaint);
                    try( ResultSet set = st.executeQuery()){
                        while( set.next()){
                            card.setId_card_patient( set.getLong(1));
                        }
                    }
                }
            });

        }catch ( Exception ex ){
        }        
        return card;
    }

    /**
     * Поиск карты пациента по документу пациента ( полис/снилс/номер )
     * @param parametr - параметр поиска
     * @return Card_patient
     * @throws Exception
     */
    public Card_patient findByNumberPolisSnils( String parametr ) throws Exception{
        String sql1 = " SELECT cp.id_card_patient, cp.diagnosis, cp.allergy, cp.note, cp.сonclusion, p.id_patient, p.surname, p.name, p.full_name, p.gender, "
                    + " p.phone, p.address, d.id_document, d.type_document, d.seria, d.numar, d.snils, d.polis  FROM Card_patient cp "
                    + " left join Patient p on p.id_patient = cp.pacient_id "
                    + " left join Document d on d.id_document = p.document_id "
                    + " WHERE d.numar = ? or d.snils = ? or d.polis = ? ";
        String sql2 = " SELECT c.functional_impairment, tc.id_type_complaint, tc.name FROM Card_patient cp "
                    + " left join Patient p on p.id_patient = cp.pacient_id"
                    + " left join Document d on d.id_document = p.document_id"
                    + " left join Card_patient_Complaint cpc on cpc.card_patient_id = cp.id_card_patient "
                    + " left join Type_complaint tc on tc.id_type_complaint = cpc.type_complaint_id "
                    + " left join Complaint c on c.id_complaint = tc.complaint_id"
                    + " WHERE d.numar = ? or d.snils = ? or d.polis = ? ";
        Session session;
        Card_patient card = new Card_patient();
            try{
            session = em.unwrap( Session.class);
            session.doWork( (Connection conn ) ->{
                try( PreparedStatement st = conn.prepareStatement( sql1 )){
                    st.setString(1, parametr);
                    st.setString(2, parametr);
                    st.setString(3, parametr);
                    try( ResultSet set = st.executeQuery()){
                        while( set.next()){
                            card.setId_card_patient( set.getLong(1));
                            card.setDiagnosis( set.getString(2));
                            card.setAllergy( set.getBoolean(3));
                            card.setNote( set.getString( 4 ));
                            card.setСonclusion( set.getString( 5 ));
                            Patient patient = new Patient();
                            patient.setId_patient( set.getLong( 6 ));
                            patient.setSurname( set.getString( 7 ));
                            patient.setName( set.getString( 8 ));
                            patient.setFull_name( set.getString( 9 ));
                            patient.setGender(  set.getInt( 10 )  == 0 ? Gender.Man : Gender.Woman );
                            patient.setPhone( set.getString( 11));
                            patient.setAddress( set.getString( 12 ));
                            Document document = new Document();
                            document.setId_document( set.getLong( 13 ));
                            document.setType_document( set.getString( 14 ));
                            document.setSeria( set.getString( 15 ));
                            document.setNumar( set.getString( 16 ));
                            document.setSnils( set.getString( 17 ));
                            document.setPolis( set.getString( 18 ));
                            patient.setDocument( document );
                            card.setPatient( patient );
                            List<TypeComplaint> list = new ArrayList<>();
                            try( PreparedStatement st2= conn.prepareStatement( sql2 )){
                                st2.setString(1, parametr);
                                st2.setString(2, parametr);
                                st2.setString(3, parametr);
                                try( ResultSet set2 = st2.executeQuery()){
                                    while( set2.next()){
                                        TypeComplaint typeComplaint = new TypeComplaint();
                                        Сomplaint complaint = new Сomplaint();
                                        complaint.setFunctional_impairment( set2.getString( 1 ));
                                        typeComplaint.setComplaint(complaint);
                                        typeComplaint.setId_type_complaint( set2.getLong(2 ));
                                        typeComplaint.setName(set2.getString( 3 ));
                                        if( typeComplaint.getComplaint().getFunctional_impairment() != null ? list.add( typeComplaint ) :  list.isEmpty());
                                    }
                                }
                            }
                            card.setTypeComplaint(list);
                        }
                    }
                }
            });

            }catch ( Exception ex ){
                java.util.logging.Logger.getLogger( CardPatientService.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }        
            return card;
        }


}
