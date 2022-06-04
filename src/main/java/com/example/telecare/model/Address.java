package com.example.telecare.model;

import javax.persistence.*;

@Entity
public class Address {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "city_id")
    private String cityId;
    @Basic
    @Column(name = "districy_id")
    private String districyId;
    @Basic
    @Column(name = "ward_id")
    private String wardId;
    @Basic
    @Column(name = "street_name")
    private String streetName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistricyId() {
        return districyId;
    }

    public void setDistricyId(String districyId) {
        this.districyId = districyId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (id != address.id) return false;
        if (cityId != null ? !cityId.equals(address.cityId) : address.cityId != null) return false;
        if (districyId != null ? !districyId.equals(address.districyId) : address.districyId != null) return false;
        if (wardId != null ? !wardId.equals(address.wardId) : address.wardId != null) return false;
        if (streetName != null ? !streetName.equals(address.streetName) : address.streetName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (districyId != null ? districyId.hashCode() : 0);
        result = 31 * result + (wardId != null ? wardId.hashCode() : 0);
        result = 31 * result + (streetName != null ? streetName.hashCode() : 0);
        return result;
    }
}
