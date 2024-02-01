package com.mohanit;

import java.time.LocalDate;

import com.mohanit.entity.ReportEntity;

public class ReportEntityTestUtil {

    public static ReportEntity createSampleReportEntity() {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setSno(1);
        reportEntity.setName("John Doe");
        reportEntity.setEmail("john.doe@example.com");
        reportEntity.setMobileNumber(1234567890L);
        reportEntity.setGender("Male");
        reportEntity.setSsn(987654321L);
        reportEntity.setPlanName("Sample Plan");
        reportEntity.setPlanStatus("Active");
        reportEntity.setStartDate(LocalDate.of(2022, 1, 1));
        reportEntity.setEndDate(LocalDate.of(2022, 12, 31));
        reportEntity.setCreatedBy("admin");
        reportEntity.setUpdatedBy("admin");
        reportEntity.setCreatedDate(LocalDate.now());
        reportEntity.setUpdatedDate(LocalDate.now());

        return reportEntity;
    }
}
