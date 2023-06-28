package com.klinik.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
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
import com.klinik.entity.Document;
import com.klinik.repositories.DocumentRepository;

@DisplayName( "Класс предназначен для тестирования сервиса DocumentService")
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class DocumentServiceTest {

    @Autowired
    private DocumentRepository repository;

    @Autowired
    private EntityManager em;

    private DocumentService service;

    private Document document = new Document();
    private List<Document> list = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        service = mock( DocumentService.class );
        service.repository = repository;
    }

    @AfterEach
    public void tearDown() {
    }

    @DisplayName("Получение списка документов")
    @Test
    public void testGetAllDocuments() throws Exception{
        Mockito.when( service.getAllDocuments()).thenCallRealMethod();
        Mockito.when( service.getAllDocuments()).thenReturn( list );
        Mockito.when( service.getAllDocuments()).then(( InvocationOnMock inv ) ->{
            return ( List<Document> ) inv.callRealMethod();
        });
        assertNotNull( service.getAllDocuments());
        Mockito.verify( service, times(1)).getAllDocuments();
    }

    @DisplayName( "Поиск документа по ИД документа")
    @Test
    public void testFindDocumentById() throws Exception{
        Long IdDocument = 1L;
        Mockito.when( service.findById( IdDocument )).thenCallRealMethod();
        Mockito.when( service.findById( IdDocument )).thenReturn(document);
        Mockito.when( service.findById( IdDocument )).then(( InvocationOnMock inv ) ->{
            return ( Document ) inv.callRealMethod();
        });
        assertNotNull( service.findById( IdDocument ));
        Mockito.verify( service, times(1)).findById( IdDocument );
    }

    @DisplayName( "Добавить документ")
    @Test
    public void testaAddDocument() throws Exception{
        Long IdDocument = 1L;
        Mockito.when( service.findById( IdDocument )).then(( InvocationOnMock inv ) ->{
            return ( Document ) inv.callRealMethod();
        });
        assertNotNull( document =  service.findById( IdDocument ));
        Mockito.when( service.addDocument(document)).thenCallRealMethod();
        Mockito.when( service.addDocument(document)).thenReturn(document);
        Mockito.when( service.addDocument(document)).then(( InvocationOnMock inv ) ->{
            return ( Document ) inv.callRealMethod();
        });
        assertNotNull( service.addDocument(document));
        Mockito.verify( service, times(1)).addDocument(document);
    }

    @DisplayName("Поиск документа по номеру")
    @Test
    public void testFindByNumar() throws Exception{
        String numar = "123243411";
        Mockito.when( service.findByNumar(numar)).thenCallRealMethod();
        Mockito.when( service.findByNumar(numar)).thenReturn(document);
        Mockito.when( service.findByNumar(numar)).then(( InvocationOnMock inv ) ->{
            return ( Document ) inv.callRealMethod();
        });
        assertNotNull( service.findByNumar(numar));
        Mockito.verify( service, times(1)).findByNumar(numar);
    }

    @DisplayName("Поиск документа по снилс")
    @Test
    public void testFindBySnils() throws Exception{
        String snils = "123-456-789-11";
        Mockito.when( service.findBySnils(snils)).thenCallRealMethod();
        Mockito.when( service.findBySnils(snils)).thenReturn(document);
        Mockito.when( service.findBySnils(snils)).then(( InvocationOnMock inv ) ->{
            return ( Document ) inv.callRealMethod();
        });
        assertNotNull( service.findBySnils(snils));
        Mockito.verify( service, times(1)).findBySnils(snils);
    }

    @DisplayName("Поиск документа по полису")
    @Test
    public void testFindByPolis() throws Exception{
        String polis = "0000 0000 0000 0111";
        Mockito.when( service.findByPolis(polis)).thenCallRealMethod();
        Mockito.when( service.findByPolis(polis)).thenReturn(document);
        Mockito.when( service.findByPolis(polis)).then(( InvocationOnMock inv ) ->{
            return ( Document ) inv.callRealMethod();
        });
        assertNotNull( service.findByPolis(polis));
        Mockito.verify( service, times(1)).findByPolis(polis);
    }


    
}
