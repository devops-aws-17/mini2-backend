package com.mohanit.service;

import java.util.List;

import com.mohanit.binding.SearchRequest;
import com.mohanit.binding.SearchResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface ReportService {

	public List<String> getPlanName();

	public List<String> getPlanStatus();
	
	public List<SearchResponse> SearchResponse(SearchRequest request);

	public void generateExcel(HttpServletResponse response) throws Exception;
	
	public void generatePdf(HttpServletResponse response)throws Exception;

}
