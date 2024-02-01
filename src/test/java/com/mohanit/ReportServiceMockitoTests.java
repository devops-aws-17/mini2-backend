package com.mohanit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.mock.web.MockHttpServletResponse;

import com.mohanit.binding.SearchRequest;
import com.mohanit.binding.SearchResponse;
import com.mohanit.entity.ReportEntity;
import com.mohanit.repo.ReportRepo;
import com.mohanit.service.ReportImplementation;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@SpringBootTest(classes = { ReportServiceMockitoTests.class })
public class ReportServiceMockitoTests {

	@Mock
	private ReportRepo repository;

	@InjectMocks
	private ReportImplementation service;

	@Test
	@Order(1)
	public void testGetPlanName() {
		List<String> expectedPlanNames = Arrays.asList("Food", "UnEmployment");

		when(repository.findByPlanName()).thenReturn(expectedPlanNames);

		List<String> result = service.getPlanName();

		assertEquals(expectedPlanNames, result);

		verify(repository).findByPlanName();
	}

	@Test
	@Order(2)
	public void testGetPlanStatus() {
		List<String> expectedPlanStatus = Arrays.asList("Access", "Access");

		when(repository.findByPlanStatus()).thenReturn(expectedPlanStatus);

		List<String> result = service.getPlanStatus();

		assertEquals(expectedPlanStatus, result);

		verify(repository).findByPlanStatus();
	}

	@Test
	@Order(3)
	public void testSearchResponse() {
		SearchRequest request = new SearchRequest();
		request.setPlanName("Plan1");
		request.setPlanStatus("Status1");
		request.setStartDate(LocalDate.parse("2022-01-01"));
		request.setEndDate(LocalDate.parse("2022-01-31"));

		ReportEntity entity1 = new ReportEntity();
		entity1.setSno(1);
		entity1.setPlanName("Plan1");
		entity1.setPlanStatus("Status1");
		entity1.setStartDate(LocalDate.parse("2022-01-05"));
		entity1.setEndDate(LocalDate.parse("2022-01-15"));

		ReportEntity entity2 = new ReportEntity();
		entity2.setSno(2);
		entity2.setPlanName("Plan1");
		entity2.setPlanStatus("Status1");
		entity2.setStartDate(LocalDate.parse("2022-01-10"));
		entity2.setEndDate(LocalDate.parse("2022-01-20"));

		List<ReportEntity> mockEntities = Arrays.asList(entity1, entity2);

		when(repository.findAll(any(Example.class))).thenReturn(mockEntities);

		List<SearchResponse> result = service.SearchResponse(request);

		assertEquals(2, result.size());
		assertEquals(1, result.get(0).getSno());
		assertEquals(2, result.get(1).getSno());

		verify(repository).findAll(any(Example.class));
	}

	@Test
	@Order(4)
	public void testGenerateExcel() throws Exception {

		List<ReportEntity> mockEntities = createMockEntities();
		when(repository.findAll()).thenReturn(mockEntities);

		MockHttpServletResponse mockResponse = new MockHttpServletResponse();

		service.generateExcel(mockResponse);

		verify(repository).findAll();

		assertExcelContent(mockEntities, mockResponse);
	}

	private List<ReportEntity> createMockEntities() {
		List<ReportEntity> mockEntities = new ArrayList<>();
		return mockEntities;
	}

	private void assertExcelContent(List<ReportEntity> entities, MockHttpServletResponse response) throws IOException {

		byte[] content = response.getContentAsByteArray();

		HSSFWorkbook workbook = new HSSFWorkbook(new ByteArrayInputStream(content));
		HSSFSheet sheet = workbook.getSheetAt(0);

		HSSFRow headerRow = sheet.getRow(0);
		assertEquals("S.NO", headerRow.getCell(0).getStringCellValue());
		assertEquals("NAME", headerRow.getCell(1).getStringCellValue());
		assertEquals("EMAIL", headerRow.getCell(2).getStringCellValue());

		for (int i = 0; i < entities.size(); i++) {
			HSSFRow dataRow = sheet.getRow(i + 1);
			assertEquals(entities.get(i).getSno(), (int) dataRow.getCell(0).getNumericCellValue());
			assertEquals(entities.get(i).getName(), dataRow.getCell(1).getStringCellValue());
			assertEquals(entities.get(i).getEmail(), dataRow.getCell(2).getStringCellValue());
		}

		workbook.close();
	}

	@Test
	@Order(5)
	public void testGeneratePdf() throws Exception {
		// Arrange
		List<ReportEntity> mockEntities = Arrays.asList(ReportEntityTestUtil.createSampleReportEntity(),
				ReportEntityTestUtil.createSampleReportEntity()
		// Add more entities as needed for testing variations
		);

		when(repository.findAll()).thenReturn(mockEntities);

		// Mocking HttpServletResponse
		HttpServletResponse response = mock(HttpServletResponse.class);
		ServletOutputStream outputStream = mock(ServletOutputStream.class);
		when(response.getOutputStream()).thenReturn(outputStream);

		// Act
		service.generatePdf(response);

		// Assert
		// Verify that repository.findAll() was called
		verify(repository).findAll();

		// Verify that response.getOutputStream() was called
		verify(response).getOutputStream();

		// Add assertions to validate the content of the generated PDF
		// For example, use a PDF parsing library to assert on the content of
		// outputStream

		// Optionally, verify other interactions or expectations
	}
}
