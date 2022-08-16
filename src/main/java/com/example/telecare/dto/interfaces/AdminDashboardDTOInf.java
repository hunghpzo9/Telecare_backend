package com.example.telecare.dto.interfaces;

public interface AdminDashboardDTOInf {
    int getNumberOfPatient();
    int getNumberOfDoctor();
    int getNumberOfActivePatient();
    int getNumberOfActiveDoctor();
    int getTotalAmountThisMonth();
    int getNumberOfPaidAppointment();
    int getNumberOfUnpaidAppointment();
    int getNumberOfCancelAppointment();
    int getCurrentListedPrice();
    int getTotalNewPatientThisQuarter();
    int getTotalNewDoctorThisQuarter();

    int getTotalAmountJan();
    int getTotalAmountFeb();
    int getTotalAmountMar();
    int getTotalAmountApr();
    int getTotalAmountMay();
    int getTotalAmountJune();
    int getTotalAmountJuly();
    int getTotalAmountAug();
    int getTotalAmountSep();
    int getTotalAmountOct();
    int getTotalAmountNov();
    int getTotalAmountDec();
}
