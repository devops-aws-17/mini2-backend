package com.mohanit.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mohanit.entity.ReportEntity;

public interface ReportRepo extends JpaRepository<ReportEntity, Serializable>{
	
	@Query("SELECT DISTINCT(planName)FROM ReportEntity")
	public List<String> findByPlanName();
	
	@Query("SELECT DISTINCT (planStatus)FROM ReportEntity")
	public List<String> findByPlanStatus();

 }
