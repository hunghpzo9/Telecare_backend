package com.example.telecare.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Medicine {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "number_of_receipts")
    private String numberOfReceipts;
    @Basic
    @Column(name = "year_of_receipts")
    private Integer yearOfReceipts;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "company")
    private String company;
    @Basic
    @Column(name = "advertising_type")
    private String advertisingType;
    @Basic
    @Column(name = "registration_number")
    private String registrationNumber;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Medicine medicine = (Medicine) o;

        if (id != medicine.id) return false;
        if (numberOfReceipts != null ? !numberOfReceipts.equals(medicine.numberOfReceipts) : medicine.numberOfReceipts != null)
            return false;
        if (yearOfReceipts != null ? !yearOfReceipts.equals(medicine.yearOfReceipts) : medicine.yearOfReceipts != null)
            return false;
        if (name != null ? !name.equals(medicine.name) : medicine.name != null) return false;
        if (company != null ? !company.equals(medicine.company) : medicine.company != null) return false;
        if (advertisingType != null ? !advertisingType.equals(medicine.advertisingType) : medicine.advertisingType != null)
            return false;
        if (registrationNumber != null ? !registrationNumber.equals(medicine.registrationNumber) : medicine.registrationNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (numberOfReceipts != null ? numberOfReceipts.hashCode() : 0);
        result = 31 * result + (yearOfReceipts != null ? yearOfReceipts.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (advertisingType != null ? advertisingType.hashCode() : 0);
        result = 31 * result + (registrationNumber != null ? registrationNumber.hashCode() : 0);
        return result;
    }
}
