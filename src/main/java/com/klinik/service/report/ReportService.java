package com.klinik.service.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import com.klinik.entity.Card_patient;
import com.klinik.entity.Doctor;
import com.klinik.entity.Gender;
import com.klinik.entity.Patient;
import com.klinik.entity.Record_patient;
import com.klinik.entity.TypeComplaint;
import com.klinik.entity.Сomplaint;
import com.klinik.excep.MyException;
import com.klinik.response.ReportDrug;
import com.klinik.response.report.CardPatinetReport;
import com.klinik.response.report.RecordPatientReport;
import com.klinik.response.report.ResponseReport;

@Service
public class ReportService {

    @PersistenceContext
    private EntityManager em;
    
    /**
     * Отчет по виду ребилитационного лечения за период времени
     * 
     * @param dateFrom - дата с 
     * @param dateTo   - дата по
     * @return List<ResponseReport>
     * @throws Exception
     * @throws MyException
     */
    public List<ResponseReport> getStatReport( LocalDateTime dateFrom, LocalDateTime dateTo) throws Exception{
        List<ResponseReport> report = new ArrayList<>();
        try{
            String sql = "SELECT t.name as name_solution, COUNT( u.rehabilitation_solution_id ) as count_solution, COUNT(DISTINCT u.card_patient_id) as count_patient FROM Treatment u" 
                       + " left join Rehabilitation_solution t on t.id_rehabilitation_solution = u.rehabilitation_solution_id"
                       + " where  u.time_start_treatment BETWEEN ? and ?  group by t.name";
            Session session;
            session = em.unwrap( Session.class );
            session.doWork(( Connection conn) ->{
                try ( PreparedStatement st = conn.prepareStatement( sql )){
                    st.setTimestamp(1 , Timestamp.valueOf( dateFrom ));
                    st.setTimestamp(2 , Timestamp.valueOf( dateTo ));
                    try( ResultSet set = st.executeQuery()){
                        while( set.next()){
                            ResponseReport responseReport = new ResponseReport();
                            responseReport.setName_rehabilitation_treatment( set.getString( 1 ));
                            responseReport.setCount_treatment( set.getLong( 2 ));
                            responseReport.setCount_patient( set.getLong( 3 ));
                            report.add(responseReport);
                        }
                    }
                }catch(SQLException ex ){
                    java.util.logging.Logger.getLogger( ReportService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }catch( Exception ex ){
            java.util.logging.Logger.getLogger( ReportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return report;
    }

    public List<ReportDrug> reportStatDrug( LocalDateTime dateFrom, LocalDateTime dateTo ) throws Exception{
        List<ReportDrug> response = new ArrayList<>();
        try{
            String request = "SELECT dt.name , COUNT( u.drug_id ) as count_drug_treatment, COUNT(DISTINCT u.card_patient_id) as count_patient FROM Treatment u "
                           + " left join Drug d on d.id_dr = u.drug_id "
                           + " left join Drug_treatment dt on dt.id_drug = d.drug_id"
                           + " where u.time_start_treatment BETWEEN ? and ? group by dt.name ";
            Session session = em.unwrap( Session.class );
            session.doWork(( Connection conn) ->{
                try( PreparedStatement ps = conn.prepareStatement( request )){
                    ps.setTimestamp( 1,Timestamp.valueOf( dateFrom ));
                    ps.setTimestamp( 2, Timestamp.valueOf( dateTo ));
                    try( ResultSet rs = ps.executeQuery() ){
                        while ( rs.next() ){
                            ReportDrug drug = new ReportDrug();
                            drug.setName_drug_treatment( rs.getString(1));
                            drug.setCount_drug_treatment( rs.getLong(2));
                            drug.setCount_patient( rs.getLong(3));
                            response.add( drug );
                        }
                    }
                }
            });               
        }catch( Exception ex ){
            java.util.logging.Logger.getLogger( ReportService.class.getName()).log(Level.SEVERE, null, ex);
        } 

        return response;
    } 

    /**
     * Отчет о полной информации по пациенту
     * 
     * @param idCardPatient - ид карты пациента
     * @return ResponsePatientReport
     * @throws Exception
     */
    public CardPatinetReport reportInformationAboutPatient(Long idCardPatient ) throws Exception{
        CardPatinetReport response =new CardPatinetReport();
        try{
            String sql = "SELECT c.diagnosis, c.allergy, c.note, c.сonclusion, p.surname, p.name, p.full_name,p.gender, p.phone, p.address,count( t.id_treatment) FROM Card_patient c"
                       + " left join Patient p on id_patient = c.pacient_id"
                       + " left join Treatment t on t.card_patient_id = c.id_card_patient"
                       + " left join Rehabilitation_solution r on r.id_rehabilitation_solution = t.rehabilitation_solution_id"
                       + " where c.id_card_patient = ? group by c.diagnosis, c.allergy, c.note, c.сonclusion, p.surname, p.name, p.full_name,p.gender, p.phone, p.address";   
            String sql2 = "SELECT t.name as name_solution, COUNT( u.rehabilitation_solution_id ) as count_solution FROM Treatment u"
                        + " left join Rehabilitation_solution t on t.id_rehabilitation_solution = u.rehabilitation_solution_id"
                        + " left join Card_patient c on  c.id_card_patient =u.card_patient_id"
                        + " where  c.id_card_patient  = ?  group by t.name";
            String sql3 = "SELECT s.name, d.functional_impairment FROM Card_patient c "
                        + " left join Patient p on id_patient = c.pacient_id "
                        + " left join Card_patient_Complaint cpc on cpc.card_patient_id = c.id_card_patient "
                        + " left join Type_complaint s on s.id_type_complaint = cpc.type_complaint_id "
                        + " left join Complaint d on d.id_complaint =  s.complaint_id "
                        + " where c.id_card_patient = ?";            
            Session session;
            session = em.unwrap( Session.class );
            session.doWork(( Connection conn) ->{
                try ( PreparedStatement st = conn.prepareStatement( sql )){
                    st.setLong(1 , idCardPatient);
                    try( ResultSet rs = st.executeQuery() ){
                        while( rs.next() ){
                            Card_patient card = new Card_patient();
                            card.setDiagnosis( rs.getString( 1 ));
                            card.setAllergy( rs.getBoolean( 2 ));
                            card.setNote( rs.getString( 3 ));
                            card.setСonclusion( rs.getString( 4 ));
                            Patient patient = new Patient();
                            patient.setSurname( rs.getString(5));
                            patient.setName( rs.getString(6));
                            patient.setFull_name( rs.getString(7));
                            patient.setGender( rs.getInt(8) == 0 ? Gender.Man : Gender.Woman );
                            patient.setPhone( rs.getString(9));
                            patient.setAddress( rs.getString(10));
                            response.setCount_treatment( rs.getInt( 11 ));
                            card.setPatient( patient );
                            List<TypeComplaint> complaints  = new ArrayList();
                                try ( PreparedStatement st3 = conn.prepareStatement( sql3 )){
                                    st3.setLong(1 , idCardPatient);
                                    try( ResultSet rs3 = st3.executeQuery() ){
                                        while( rs3.next() ){
                                            TypeComplaint typeComplaint = new TypeComplaint();
                                            typeComplaint.setName( rs3.getString( 1 ));
                                            Сomplaint complaint = new Сomplaint();
                                            complaint.setFunctional_impairment( rs3.getString( 2 ));
                                            typeComplaint.setComplaint( complaint );
                                            complaints.add( typeComplaint );
                                        }
                                    }
                                }
                                        card.setTypeComplaint( complaints );
                                        List<ResponseReport> treatment = new ArrayList<>();
                                        try ( PreparedStatement st2 = conn.prepareStatement( sql2 )){
                                            st2.setLong(1 , idCardPatient);
                                            try( ResultSet rs2 = st2.executeQuery() ){
                                                while( rs2.next() ){
                                                    ResponseReport responseReport = new ResponseReport();
                                                    responseReport.setCount_treatment( rs2.getLong( 2 ));
                                                    responseReport.setName_rehabilitation_treatment( rs2.getString( 1 ));
                                                    treatment.add( responseReport);
                                                    response.setTreatment( treatment );
                                                }
                                            }
                                        }
                            response.setCard( card );
                        }
                    }
                }catch(SQLException ex ){
                    java.util.logging.Logger.getLogger( ReportService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }catch( Exception ex ){
            java.util.logging.Logger.getLogger( ReportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    /**
     * Отчет по записям пациента за период времени
     * 
     * @param IdPatient
     * @param dateFrom
     * @param dateTo
     * @return
     * @throws Exception
     */
    public RecordPatientReport reportByPatietnWithRecordPatient( Long IdPatient, LocalDateTime dateFrom,LocalDateTime dateTo ) throws Exception{
        RecordPatientReport report = new RecordPatientReport();
        try{
            String SQL = "SELECT p.surname, p.name, p.full_name, p.gender, p.phone, p.address, c.diagnosis, c.allergy, c.note, c.сonclusion, count(r.id_record) FROM Patient p " 
                       + " left join Card_patient c on c.pacient_id = p.id_patient "
                       + " left join Record_patient r on r.card_patient_id = c.id_card_patient "
                       + " where p.id_patient = ? and r.date_record BETWEEN ? and ?"
                       + " GROUP BY p.surname, p.name, p.full_name, p.gender, p.phone, p.address, c.diagnosis, c.allergy, c.note, c.сonclusion";
            String SQL2 = "SELECT r.id_record, r.date_record, r.date_appointment, r.number_room,d.id_doctor, d.surname, d.name, d.full_name  FROM Record_patient r "
                        + " left join  Doctor d on d.id_doctor = r.doctor_id"
                        + " left join Card_patient c on c.id_card_patient = card_patient_id"
                        + " left join Patient p on p.id_patient = c.pacient_id"
                        + " where p.id_patient = ? and r.date_record BETWEEN ? and ?";
            String SQL3 = "SELECT s.name FROM Card_patient c "
                        + " left join Patient p on id_patient = c.pacient_id "
                        + " left join Card_patient_Complaint cpc on cpc.card_patient_id = c.id_card_patient "
                        + " left join Type_complaint s on s.id_type_complaint = cpc.type_complaint_id "
                        + " where p.id_patient = ?";     
            Session session;
            session = em.unwrap( Session.class );
            session.doWork(( Connection conn ) ->{
                try( PreparedStatement ps = conn.prepareStatement( SQL )){
                    ps.setLong( 1, IdPatient );
                    ps.setTimestamp( 2, Timestamp.valueOf( dateFrom ));
                    ps.setTimestamp( 3, Timestamp.valueOf( dateTo ));
                    try( ResultSet rs = ps.executeQuery() ){
                        while( rs.next() ){
                            Patient patient = new Patient();
                            Card_patient card = new Card_patient();
                            List<Record_patient> list = new ArrayList<>();
                            patient.setSurname( rs.getString( 1 ));
                            patient.setName( rs.getString( 2 ));
                            patient.setFull_name( rs.getString( 3 ));
                            patient.setGender( rs.getInt( 4 ) == 0 ? Gender.Man : Gender.Woman );
                            patient.setPhone( rs.getString( 5 ));
                            patient.setAddress( rs.getString( 6 ));
                            card.setDiagnosis( rs.getString( 7 ));
                            card.setAllergy( rs.getBoolean( 8 ));
                            card.setNote( rs.getString( 9 ));
                            card.setСonclusion( rs.getString( 10 ));
                            card.setPatient( patient );
                            try( PreparedStatement ps2 = conn.prepareStatement( SQL2 )){
                                ps2.setLong( 1, IdPatient );
                                ps2.setTimestamp( 2, Timestamp.valueOf( dateFrom ));
                                ps2.setTimestamp( 3, Timestamp.valueOf( dateTo ));
                                try( ResultSet rs2 = ps2.executeQuery() ){
                                    while( rs2.next() ){
                                    Record_patient record_patient = new Record_patient();
                                    Doctor doctor = new Doctor();
                                    record_patient.setId_record(rs2.getLong(1));
                                    record_patient.setDate_record( rs2.getTimestamp(2).toLocalDateTime() );
                                    record_patient.setDate_appointment( rs2.getTimestamp(3).toLocalDateTime());
                                    record_patient.setNumber_room( rs2.getLong( 4 ));
                                    doctor.setId_doctor( rs2.getLong( 5));
                                    doctor.setSurname( rs2.getString( 6 ));
                                    doctor.setName( rs2.getString( 7 ));
                                    doctor.setFull_name( rs2.getString( 8 ));
                                    record_patient.setDoctor( doctor );  
                                    list.add( record_patient );  
                                        }
                                    }
                                }
                                List<TypeComplaint> listTypeComplaint = new ArrayList<>();
                                    try ( PreparedStatement ps3 = conn.prepareStatement( SQL3 )){
                                        ps3.setLong(1 , IdPatient );
                                        try( ResultSet rs3 = ps3.executeQuery() ){
                                            while( rs3.next() ){
                                                TypeComplaint typeComplaint = new TypeComplaint();
                                                typeComplaint.setName(rs3.getString(1));
                                                listTypeComplaint.add( typeComplaint );
                                            }
                                        }
                                    } 
                            card.setTypeComplaint( listTypeComplaint );
                            report.setCard( card );
                            report.setCount_record_for_time( rs.getLong( 11 ));
                            report.setListRecordPatient( list );
                        }
                    }
                }catch( SQLException ex ){
                    java.util.logging.Logger.getLogger( ReportService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        }catch( Exception ex ){
            java.util.logging.Logger.getLogger( ReportService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return report;
    }
        
       
    
}
