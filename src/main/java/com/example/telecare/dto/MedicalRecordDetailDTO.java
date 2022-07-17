package com.example.telecare.dto;

public interface MedicalRecordDetailDTO {
    Integer getId();
    String getMedicalRecordName();
    String getReason();
    String getMainDisease();
    String getUrl();
    Integer getType();
    String getExternal();
    String getGuardianDetail();
    String getGuardianPhone();
    String getMedicalProcess();
    String getSelfHistory();
    String getFamilyHistory();
    String getBodyExamination();
    String getOrgansExamination();
    String getSummary();
    String getIncludeDisease();
    String getFirstAmount();
    String getSecondAmount();
    Integer getIsEdited();
}
