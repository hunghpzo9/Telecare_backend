package com.example.telecare.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Payment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "appointment_id")
    private int appointmentId;
    @Basic
    @Column(name = "bankcode")
    private String bankcode;
    @Basic
    @Column(name = "banktran_no")
    private String banktranNo;
    @Basic
    @Column(name = "cardtype")
    private String cardtype;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "transaction_date")
    private String transactionDate;
    @Basic
    @Column(name = "transaction_no")
    private String transactionNo;
    @Basic
    @Column(name = "trace")
    private String trace;
    @Basic
    @Column(name = "installment")
    private String installment;
    @Basic
    @Column(name = "status")
    private Integer status;
    @Basic
    @Column(name = "amount")
    private String amount;
    @Basic
    @Column(name = "response_code")
    private String responseCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getBanktranNo() {
        return banktranNo;
    }

    public void setBanktranNo(String banktranNo) {
        this.banktranNo = banktranNo;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getInstallment() {
        return installment;
    }

    public void setInstallment(String installment) {
        this.installment = installment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (id != payment.id) return false;
        if (appointmentId != payment.appointmentId) return false;
        if (bankcode != null ? !bankcode.equals(payment.bankcode) : payment.bankcode != null) return false;
        if (banktranNo != null ? !banktranNo.equals(payment.banktranNo) : payment.banktranNo != null) return false;
        if (cardtype != null ? !cardtype.equals(payment.cardtype) : payment.cardtype != null) return false;
        if (description != null ? !description.equals(payment.description) : payment.description != null) return false;
        if (transactionDate != null ? !transactionDate.equals(payment.transactionDate) : payment.transactionDate != null)
            return false;
        if (transactionNo != null ? !transactionNo.equals(payment.transactionNo) : payment.transactionNo != null)
            return false;
        if (trace != null ? !trace.equals(payment.trace) : payment.trace != null) return false;
        if (installment != null ? !installment.equals(payment.installment) : payment.installment != null) return false;
        if (status != null ? !status.equals(payment.status) : payment.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + appointmentId;
        result = 31 * result + (bankcode != null ? bankcode.hashCode() : 0);
        result = 31 * result + (banktranNo != null ? banktranNo.hashCode() : 0);
        result = 31 * result + (cardtype != null ? cardtype.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (transactionDate != null ? transactionDate.hashCode() : 0);
        result = 31 * result + (transactionNo != null ? transactionNo.hashCode() : 0);
        result = 31 * result + (trace != null ? trace.hashCode() : 0);
        result = 31 * result + (installment != null ? installment.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}
