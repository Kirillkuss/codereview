package com.klinik.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.klinik.repositories.DocumentRepository;
import com.klinik.response.BaseResponse;
import com.klinik.service.DocumentService;

@DisplayName( "Класс предназначен для тестирования конторллера DocumentController")
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class DocumentControllerTest {

    private DocumentController documentController;

    private DocumentService service;

    @Autowired
    DocumentRepository repository;

    @BeforeEach
    public void setUp() {
        documentController         = mock( DocumentController.class );
        repository                 = mock( DocumentRepository.class );
        service                    = mock( DocumentService.class );
        service.repository         = repository;
        documentController.service = service;

    }

    @AfterEach
    public void tearDown() {
    }

    @DisplayName( "Получение списка всех пользователей")
    @Test
    public void testGetAllDocuments() throws Exception{
        BaseResponse response = new BaseResponse<>();
        Mockito.when( documentController.getAllDocuments() ).thenCallRealMethod();
        Mockito.when( documentController.getAllDocuments()).thenReturn( response );
        Mockito.when( documentController.getAllDocuments() ).then(( InvocationOnMock inv )->{
            return ( BaseResponse ) inv.callRealMethod();
        });
        assertNotNull( documentController.getAllDocuments());
        Mockito.verify( documentController, times(1 )).getAllDocuments();
    }
    
}
