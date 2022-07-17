package com.example.telecare.model;

import javax.persistence.*;

@Entity
@Table(name = "prescription_medicine", schema = "telecare", catalog = "")
@IdClass(PrescriptionMedicinePK.class)
public class PrescriptionMedicine {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "prescription_id")
    private int prescriptionId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "medicine_id")
    private int medicineId;
    @Basic
    @Column(name = "instruction")
    private String instruction;


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

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrescriptionMedicine that = (PrescriptionMedicine) o;

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
