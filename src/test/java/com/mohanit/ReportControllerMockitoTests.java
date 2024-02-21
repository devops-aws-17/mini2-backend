package com.mohanit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mohanit.binding.SearchRequest;
import com.mohanit.binding.SearchResponse;
import com.mohanit.rest.ReportRestController;
import com.mohanit.service.ReportService;

import jakarta.servlet.http.HttpServletResponse;

@SpringBootTest(classes = { ReportControllerMockitoTests.class })
public class ReportControllerMockitoTests {

    @Mock
    private ReportService service;

    @InjectMocks
    private ReportRestController controller;

    @Test
    @Order(1)
    public void testGetUniqueName() {
    	
        List<String> mockPlanNames = Arrays.asList("Plan1", "Plan2", "Plan3");
        when(service.getPlanName()).thenReturn(mockPlanNames);

        ResponseEntity<List<String>> responseEntity = controller.getUniqueName();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockPlanNames, responseEntity.getBody());
        verify(service).getPlanName();
    }
    
    @Test
    @Order(2)
    public void testGetUniqueStatus() {

        List<String> mockPlanStatus = Arrays.asList("Active", "Inactive", "Pending");
        when(service.getPlanStatus()).thenReturn(mockPlanStatus);

        ResponseEntity<List<String>> responseEntity = controller.getUniqueStatus();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockPlanStatus, responseEntity.getBody());
        verify(service).getPlanStatus();
    }
    
    @Test
    @Order(3)
    public void testGetSearchResponse() {
        SearchRequest mockSearchRequest = new SearchRequest();
        List<SearchResponse> mockSearchResponse = Arrays.asList(
                new SearchResponse(),
                new SearchResponse()
        );
        when(service.SearchResponse(mockSearchRequest)).thenReturn(mockSearchResponse);

        ResponseEntity<List<SearchResponse>> responseEntity = controller.getSearchResponse(mockSearchRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockSearchResponse, responseEntity.getBody());

        verify(service).SearchResponse(mockSearchRequest);
    }
    
    @Test
    @Order(4)
    public void testGenerateExcel() throws Exception {

        HttpServletResponse mockResponse = mock(HttpServletResponse.class);

        controller.generateExcel(mockResponse);

        verify(mockResponse).setContentType("application/octet-stream");
        verify(mockResponse).setHeader("Content-Disposition", "attachment; filename=SearchResponse.xls");

        verify(service).generateExcel(mockResponse);
    }
    
    @Test
    @Order(5)
    public void testGeneratePdf() throws Exception {
    	
        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
        
        controller.generatePdf(mockResponse);

        verify(mockResponse).setContentType("application/pdf");
        verify(mockResponse).setHeader("Content-Disposition", "attachment; filename=SearchResponse.pdf");

        verify(service).generatePdf(mockResponse);
    }
}
