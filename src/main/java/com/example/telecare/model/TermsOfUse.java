package com.example.telecare.model;

import javax.persistence.*;

@Entity
@Table(name = "terms_of_use", schema = "telecare", catalog = "")
public class TermsOfUse {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "url")
    private String url;
    @Basic
    @Column(name = "isUse")
    private Byte isUse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Byte getIsUse() {
        return isUse;
    }

    public void setIsUse(Byte isUse) {
        this.isUse = isUse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TermsOfUse that = (TermsOfUse) o;

        if (id != that.id) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (isUse != null ? !isUse.equals(that.isUse) : that.isUse != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (isUse != null ? isUse.hashCode() : 0);
        return result;
    }
}
