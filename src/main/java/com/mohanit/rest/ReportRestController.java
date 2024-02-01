package com.mohanit.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mohanit.binding.SearchRequest;
import com.mohanit.binding.SearchResponse;
import com.mohanit.service.ReportService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ReportRestController {

	@Autowired
	private ReportService service;
	
	@GetMapping("/plannames")
	public ResponseEntity<List<String>> getUniqueName(){
		List<String> planName = service.getPlanName();
		return new ResponseEntity<>(planName, HttpStatus.OK);
	}
	
	@GetMapping("/planstatus")
	public ResponseEntity<List<String>> getUniqueStatus(){
		List<String> planStatus = service.getPlanStatus();
		return new ResponseEntity<>(planStatus, HttpStatus.OK);
	}
	
	@PostMapping("/searchresponse")
	public ResponseEntity<List<SearchResponse>> getSearchResponse(@RequestBody SearchRequest request){
		List<SearchResponse> searchResponse = service.SearchResponse(request);
		return new ResponseEntity<List<SearchResponse>>(searchResponse, HttpStatus.OK);
	}
	
	@GetMapping("/excel")
	public void generateExcel(HttpServletResponse response) throws Exception{
		response.setContentType("application/octet-stream");
		String headerkey = "Content-Disposition";
		String headerValue = "attachment; filename=SearchResponse.xls";
		response.setHeader(headerkey, headerValue);
		service.generateExcel(response);
	}
	
	@GetMapping("/pdf")
	public void generatePdf(HttpServletResponse response)throws Exception{
		response.setContentType("application/pdf");
		String headerkey = "Content-Disposition";
		String headerValue = "attachment; filename=SearchResponse.pdf";
		response.setHeader(headerkey, headerValue);
		service.generatePdf(response);
	}
}
