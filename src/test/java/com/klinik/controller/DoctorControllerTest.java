package com.klinik.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.klinik.KlinikApplication;
import com.klinik.entity.Doctor;
import com.klinik.response.BaseResponse;
import com.klinik.service.DoctorService;

@DisplayName("Тестирования сервиса DoctorController")
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true", classes = KlinikApplication.class)
public class DoctorControllerTest {

    @Autowired
	private DoctorController controller;

    private Doctor doctor = new Doctor();
    private BaseResponse response = new BaseResponse<>();

    @BeforeEach
    public void setUp() {
        controller = mock( DoctorController.class );
        controller.service = mock( DoctorService.class);
    }

    @AfterEach
    public void tearDown() {
    }

    @DisplayName( "Получение всех докторов")
    @Test
    public void testGetAllDoc() throws Exception{
        Mockito.when( controller.getAllDoc()).thenCallRealMethod();
        Mockito.when( controller.getAllDoc()).thenReturn( response );
        Mockito.when( controller.getAllDoc()).then(( InvocationOnMock inv ) ->{
            return ( BaseResponse ) inv.callRealMethod();
        });
        assertEquals(controller.getAllDoc(), controller.getAllDoc()); 
        Mockito.verify( controller, times( 2 )).getAllDoc();
    }

    @DisplayName( "Получение списка докторов по ФИО")
    @Test
    public void testFindByFIO() throws Exception{
        Mockito.when( controller.findByFIO( "Морозов" )).thenCallRealMethod();
       // Mockito.when( controller.findByFIO( "Морозов" )).thenThrow( new MyException() );
       /** Mockito.when( controller.findByFIO( "test" )).then(( InvocationOnMock inv ) ->{
            return ( BaseResponse ) inv.callRealMethod();
        });
        assertEquals(controller.findByFIO( "test" ), controller.findByFIO( "test" )); 
        Mockito.verify( controller, times( 2 )).findByFIO( "test" );*/
    }

    @DisplayName( "Добавление доктора")
    @Test
    public void testAddDoctor() throws Exception{
        Mockito.when( controller.addDoctor( doctor )).thenCallRealMethod();
        Mockito.when( controller.addDoctor( doctor )).thenReturn( response );
        Mockito.when( controller.addDoctor( doctor )).then(( InvocationOnMock inv ) ->{
            return ( BaseResponse ) inv.callRealMethod();
        });
        assertEquals(controller.addDoctor( doctor ), controller.addDoctor( doctor )); 
        Mockito.verify( controller, times( 2 )).addDoctor( doctor );
    }
    
}
