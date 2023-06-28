package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.klinik.KlinikApplication;
import com.klinik.controller.DoctorController;
import com.klinik.entity.Doctor;
import com.klinik.repositories.DoctorRerository;

@DisplayName("Тестирования сервиса DoctorService")
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true", classes = KlinikApplication.class)
public class DoctorServiceTest {

    @Autowired 
    private DoctorService service;

    private List<Doctor> list = new ArrayList<>();
    private Doctor doctor = new Doctor();

    @BeforeEach
    public void setUp() {
        service = mock( DoctorService.class );
        service.repository = mock( DoctorRerository.class);
    }

    @AfterEach
    public void tearDown() {
    }

    @DisplayName( "Поиск доктора по Ид")
    @Test
	public void testFindById() throws Exception {
        Mockito.when( service.findById( 1L )).thenCallRealMethod();
        Mockito.when( service.findById( 1L )).thenReturn( doctor );
        Mockito.when( service.findById( 1L )).then(( InvocationOnMock inv ) ->{
            return ( Doctor ) inv.callRealMethod();
        });
        assertEquals(service.findById( 1L ), service.findById( 1L )); 
        Mockito.verify( service, times( 2 )).findById( 1L );
	}
    
    @DisplayName("Получение списка всех докторов")
    @Test
    public void testFindAll() throws Exception{
        Mockito.when( service.allDoctor() ).thenCallRealMethod();
        Mockito.when( service.allDoctor() ).thenReturn(list);
        Mockito.when( service.allDoctor() ).thenAnswer(( InvocationOnMock inv ) ->{
            return ( List<Doctor>) inv.callRealMethod();
        });
        assertNotNull( service.allDoctor() );
        Mockito.verify( service, times( 1 )).allDoctor();
    }

    @DisplayName("Поиск докторов по ФИО")
    @Test
    public void testFindByFIO() throws Exception{
        String word = "test";
        Mockito.when( service.findByFIO( word )).thenCallRealMethod();
        Mockito.when( service.findByFIO( word )).thenReturn( list );
        Mockito.when( service.findByFIO( word )).then(( InvocationOnMock inv ) ->{
            return ( List<Doctor> ) inv.callRealMethod();
        });
        assertNotNull( service.findByFIO( word ));
        Mockito.verify( service, times( 1 )).findByFIO( word );
    }

    @DisplayName("Добавлени доктора")
    @Test
    public void testSaveDoctor() throws Exception{
        Mockito.when( service.saveDoctor( doctor )).thenCallRealMethod();
        Mockito.when( service.saveDoctor( doctor )).thenReturn( doctor );
        Mockito.when( service.saveDoctor( doctor )).then(( InvocationOnMock inv ) ->{
            return ( Doctor ) inv.callRealMethod();
        });
        assertEquals( service.saveDoctor( doctor ), service.saveDoctor( doctor ));
        Mockito.verify( service, times( 2 )).saveDoctor( doctor );
    }
  
}
