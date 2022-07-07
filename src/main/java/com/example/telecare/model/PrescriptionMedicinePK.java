package com.example.telecare.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class PrescriptionMedicinePK implements Serializable {
    @Column(name = "prescription_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prescriptionId;
    @Column(name = "medicine_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medicineId;

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrescriptionMedicinePK that = (PrescriptionMedicinePK) o;

        if (prescriptionId != that.prescriptionId) return false;
        if (medicineId != that.medicineId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = prescriptionId;
        result = 31 * result + medicineId;
        return result;
    }
}
