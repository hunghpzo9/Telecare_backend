package com.example.telecare.dto;


import java.util.List;

public interface PrescriptionDetailDTO {
    Integer getId();
    String getPrescriptionDiagnosis();
    String getNote();
    String getGuardian();
    String getUrl();
    List<MedicinePrescriptionDTO> getListMedicine();
}

