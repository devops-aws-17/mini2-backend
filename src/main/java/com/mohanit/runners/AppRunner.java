package com.mohanit.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import com.mohanit.entity.ReportEntity;
import com.mohanit.repo.ReportRepo;

@Configuration
public class AppRunner implements ApplicationRunner{
	
	@Autowired
	private ReportRepo repository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		ReportEntity entity1 = new ReportEntity();
		entity1.setSno(1);
		entity1.setName("Mohan");
		entity1.setEmail("mohan@gmail.com");
		entity1.setMobileNumber(8892902211L);
		entity1.setGender("Male");
		entity1.setSsn(9982819L);
		entity1.setPlanName("Food");
		entity1.setPlanStatus("Access");
		repository.save(entity1);
		
		ReportEntity entity2 = new ReportEntity();
		entity2.setSno(2);
		entity2.setName("Naveen");
		entity2.setEmail("naveen@gmail.com");
		entity2.setMobileNumber(871831199L);
		entity2.setGender("Male");
		entity2.setSsn(9982812L);
		entity2.setPlanName("UnEmployment");
		entity2.setPlanStatus("Access");
		repository.save(entity2);
		
		ReportEntity entity3 = new ReportEntity();
		entity3.setSno(3);
		entity3.setName("Sanjay");
		entity3.setEmail("sanjay@gmail.com");
		entity3.setMobileNumber(871831199L);
		entity3.setGender("Male");
		entity3.setSsn(9982812L);
		entity3.setPlanName("Child Plan");
		entity3.setPlanStatus("Denied");
		repository.save(entity3);
		
		ReportEntity entity4 = new ReportEntity();
		entity4.setSno(4);
		entity4.setName("Pujitha");
		entity4.setEmail("pujitha@gmail.com");
		entity4.setMobileNumber(9871831199L);
		entity4.setGender("Female");
		entity4.setSsn(9982812L);
		entity4.setPlanName("Health");
		entity4.setPlanStatus("Terminated");
		repository.save(entity4);
		
		ReportEntity entity5 = new ReportEntity();
		entity5.setSno(5);
		entity5.setName("Tejaswi");
		entity5.setEmail("tejaswi@gmail.com");
		entity5.setMobileNumber(771831199L);
		entity5.setGender("Female");
		entity5.setSsn(9982812L);
		entity5.setPlanName("childcare");
		entity5.setPlanStatus("Denied");
		repository.save(entity5);
		
		ReportEntity entity6 = new ReportEntity();
		entity6.setSno(6);
		entity6.setName("Sai");
		entity6.setEmail("sai@gmail.com");
		entity6.setMobileNumber(771831199L);
		entity6.setGender("Male");
		entity6.setSsn(9982812L);
		entity6.setPlanName("childcare");
		entity6.setPlanStatus("Terminated");
		repository.save(entity6);
		
		ReportEntity entity7 = new ReportEntity();
		entity7.setSno(7);
		entity7.setName("Sujata");
		entity7.setEmail("sujata@gmail.com");
		entity7.setMobileNumber(771831199L);
		entity7.setGender("Female");
		entity7.setSsn(9982812L);
		entity7.setPlanName("childcare");
		entity7.setPlanStatus("Denied");
		repository.save(entity7);
	}

}
