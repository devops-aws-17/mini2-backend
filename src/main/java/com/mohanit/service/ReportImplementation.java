package com.mohanit.service;

import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mohanit.binding.SearchRequest;
import com.mohanit.binding.SearchResponse;
import com.mohanit.entity.ReportEntity;
import com.mohanit.repo.ReportRepo;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportImplementation implements ReportService {

	@Autowired
	private ReportRepo repository;

	@Override
	public List<String> getPlanName() {

		List<String> findByPlanName = repository.findByPlanName();

		return findByPlanName;
	}

	@Override
	public List<String> getPlanStatus() {

		List<String> findByPlanStatus = repository.findByPlanStatus();
		return findByPlanStatus;
	}

	@Override
	public List<SearchResponse> SearchResponse(SearchRequest request) {

		ReportEntity queryBuilder = new ReportEntity();

		String planName = request.getPlanName();

		if (planName != null && !planName.equals("")) {
			queryBuilder.setPlanName(planName);
		}

		String planStatus = request.getPlanStatus();

		if (planStatus != null && !planStatus.equals("")) {
			queryBuilder.setPlanStatus(planStatus);
		}

		
		  LocalDate startDate = request.getStartDate();
		  
		  if (startDate != null) { queryBuilder.setStartDate(startDate); }
		  
		  LocalDate endDate = request.getEndDate();
		  
		  if (endDate != null) { queryBuilder.setEndDate(endDate); }
		 

		Example<ReportEntity> example = Example.of(queryBuilder);

		ArrayList<SearchResponse> response = new ArrayList<>();

		List<ReportEntity> findAll = repository.findAll(example);

		for (ReportEntity entity : findAll) {

			SearchResponse searchResponse = new SearchResponse();
			BeanUtils.copyProperties(entity, searchResponse);
			response.add(searchResponse);
		}
		return response;
	}

	@Override
	public void generateExcel(HttpServletResponse response) throws Exception {

		List<ReportEntity> findAll = repository.findAll();

		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		HSSFSheet createSheet = hssfWorkbook.createSheet();
		HSSFRow headerRow = createSheet.createRow(0);
		headerRow.createCell(0).setCellValue("S.NO");
		headerRow.createCell(1).setCellValue("NAME");
		headerRow.createCell(2).setCellValue("EMAIL");
		headerRow.createCell(3).setCellValue("MOBILE NUMBER");
		headerRow.createCell(4).setCellValue("GENDER");
		headerRow.createCell(5).setCellValue("SSN");
		headerRow.createCell(6).setCellValue("PLAN NAME");

		int i = 1;
		for (ReportEntity entity : findAll) {

			HSSFRow dataRow = createSheet.createRow(i);
			dataRow.createCell(0).setCellValue(entity.getSno());
			dataRow.createCell(1).setCellValue(entity.getName());
			dataRow.createCell(2).setCellValue(entity.getEmail());
			dataRow.createCell(3).setCellValue(entity.getMobileNumber());
			dataRow.createCell(4).setCellValue(entity.getGender());
			dataRow.createCell(5).setCellValue(entity.getSsn());
			dataRow.createCell(6).setCellValue(entity.getPlanName());

			i++;
		}
		ServletOutputStream outputStream = response.getOutputStream();
		hssfWorkbook.write(outputStream);
		outputStream.close();
	}

	@Override
	public void generatePdf(HttpServletResponse response) throws Exception {

		List<ReportEntity> findAll = repository.findAll();

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTiltle.setSize(20);
		fontTiltle.setColor(Color.BLUE);

		Paragraph paragraph = new Paragraph("Search Report", fontTiltle);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(paragraph);

		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100f);
		table.setWidths(new int[] { 3, 3, 3, 3, 3, 3, 3, 3 });
		table.setSpacingBefore(5);

		PdfPCell cell = new PdfPCell();

		cell.setBackgroundColor(CMYKColor.MAGENTA);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(CMYKColor.BLACK);

		cell.setPhrase(new Phrase("S.NO", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("NAME", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("EMAIL", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("MOBILE NUMBER", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("GENDER", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("SSN", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("PLAN NAME", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("PLAN STATUS", font));
		table.addCell(cell);

		for (ReportEntity entity : findAll) {
			table.addCell(String.valueOf(entity.getSno()));
			table.addCell(entity.getName());
			table.addCell(entity.getEmail());
			table.addCell(String.valueOf(entity.getMobileNumber()));
			table.addCell(entity.getGender());
			table.addCell(String.valueOf(entity.getSsn()));
			table.addCell(entity.getPlanName());
			table.addCell(entity.getPlanStatus());
		}

		document.add(table);
		document.close();

	}
	
}
