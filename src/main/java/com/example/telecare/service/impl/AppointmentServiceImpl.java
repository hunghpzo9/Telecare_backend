package com.example.telecare.service.impl;

import com.example.telecare.dto.interfaces.*;
import com.example.telecare.enums.AppointmentStatus;
import com.example.telecare.enums.PaymentStatus;
import com.example.telecare.exception.BadRequestException;
import com.example.telecare.exception.NotFoundException;
import com.example.telecare.model.*;
import com.example.telecare.repository.*;
import com.example.telecare.service.AppointmentService;
import com.example.telecare.utils.Constants;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AppointmentDetailRepository appointmentDetailRepository;
    @Autowired
    CancelAppointmentRepository cancelAppointmentRepository;
    @Autowired
    PatientServiceImpl patientService;
    @Autowired
    DoctorServiceImpl doctorService;
    @Autowired
    RelativeServiceImpl relativeService;
    @Autowired
    EthnicServiceImpl ethnicService;
    @Autowired
    AddressServiceImpl addressService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationServiceImpl notificationService;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;
    @Autowired
    PrescriptionRepository prescriptionRepository;
    @Autowired
    ListedPriceRepository listedPriceRepository;
    @Autowired
    FeedbackRepository feedbackRepository;

    @Override
    public List<AppointmentDTOInf> findAppointmentByPatient(int id, List<Integer> statusId) {

        List<AppointmentDTOInf> appointmentList = appointmentRepository.findAppointmentByPatient(id, statusId);
        List<AppointmentDTOInf> returnAppointmentList = new ArrayList<>();
        for (AppointmentDTOInf appointmentDTO : appointmentList) {
            AppointmentDTOInf finalAppointmentDTO = appointmentDTO;
            PatientDTOInf patient = patientService.findPatientById(finalAppointmentDTO.getPatientId());
            Relative relative = null;
            if (appointmentDTO.getRelativeId() != null) {
                relative = relativeService.findRelativeById(appointmentDTO.getRelativeId());
            }
            Relative finalRelative = relative;
            appointmentDTO = new AppointmentDTOInf() {
                @Override
                public Integer getId() {
                    return finalAppointmentDTO.getId();
                }

                @Override
                public Integer getDoctorId() {
                    return finalAppointmentDTO.getDoctorId();
                }

                @Override
                public Integer getPatientId() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return null;
                }

                @Override
                public Time getStartAt() {
                    return finalAppointmentDTO.getStartAt();
                }

                @Override
                public Time getEndAt() {
                    return finalAppointmentDTO.getEndAt();
                }

                @Override
                public String getTime() {
                    return finalAppointmentDTO.getTime();
                }

                @Override
                public String getStatus() {
                    return finalAppointmentDTO.getStatus();
                }

                @Override
                public Integer getStatusId() {
                    return finalAppointmentDTO.getStatusId();
                }

                @Override
                public Integer getRelativeId() {
                    return null;
                }

                @Override
                public Integer getAmount() {
                    return null;
                }

                @Override
                public Byte getIsShare() {
                    return null;
                }

                @Override
                public Byte getIsAdd() {
                    return null;
                }

                @Override
                public Boolean getIsFeedback() {
                    var feedback = feedbackRepository.findFeedbackByAppointmentId(finalAppointmentDTO.getId());
                    return feedback != null;
                }

                @Override
                public String getCancelUserName() {
                    return null;
                }

                @Override
                public String getCancelDescription() {
                    return null;
                }

                @Override
                public String getCancelReason() {
                    return null;
                }

                @Override
                public String getPatientName() {
                    if (finalRelative != null) return finalRelative.getFullName();
                    return patient.getFullName();
                }

                @Override
                public String getPatientImageUrl() {
                    return null;
                }

                @Override
                public Byte getPatientGender() {
                    return null;
                }

                @Override
                public String getPatientPhone() {
                    return patient.getPhone();
                }

                @Override
                public Date getPatientDob() {
                    return null;
                }

                @Override
                public String getPatientEthnic() {
                    return null;
                }

                @Override
                public String getPatientEmail() {
                    return null;
                }

                @Override
                public String getPatientAddress() {
                    return null;
                }

                @Override
                public String getDoctorName() {
                    return finalAppointmentDTO.getDoctorName();
                }

                @Override
                public String getDoctorImageUrl() {
                    return finalAppointmentDTO.getDoctorImageUrl();
                }

                @Override
                public Byte getDoctorGender() {
                    return null;
                }

                @Override
                public String getDoctorSpecialty() {
                    return finalAppointmentDTO.getDoctorSpecialty();
                }

                @Override
                public String getDoctorPhone() {
                    return finalAppointmentDTO.getDoctorPhone();
                }

                @Override
                public String getDoctorEmail() {
                    return null;
                }

                @Override
                public String getDoctorJobPlace() {
                    return null;
                }

                @Override
                public String getMedicalRecordUrl() {
                    return null;
                }

                @Override
                public String getPrescriptionUrl() {
                    return null;
                }
            };
            returnAppointmentList.add(appointmentDTO);
        }
        return returnAppointmentList;
    }

    @Override
    public List<AppointmentDTOInf> findAppointmentByDoctor(int id, List<Integer> statusId) {

        List<AppointmentDTOInf> appointmentList = appointmentRepository.findAppointmentByDoctor(id, statusId);

        List<AppointmentDTOInf> returnAppointmentList = new ArrayList<>();

        for (AppointmentDTOInf appointmentDTO : appointmentList) {

            AppointmentDTOInf finalAppointmentDTO = appointmentDTO;
            DoctorDTOInf doctorDTOInf = doctorService.findDoctorById(finalAppointmentDTO.getDoctorId());
            Relative relative = null;
            if (appointmentDTO.getRelativeId() != null) {
                relative = relativeService.findRelativeById(appointmentDTO.getRelativeId());
            }
            Relative finalRelative = relative;

            appointmentDTO = new AppointmentDTOInf() {
                @Override
                public Integer getId() {
                    return finalAppointmentDTO.getId();
                }

                @Override
                public Integer getDoctorId() {
                    return finalAppointmentDTO.getDoctorId();
                }

                @Override
                public Integer getPatientId() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return null;
                }

                @Override
                public Time getStartAt() {
                    return finalAppointmentDTO.getStartAt();
                }

                @Override
                public Time getEndAt() {
                    return finalAppointmentDTO.getEndAt();
                }

                @Override
                public String getTime() {
                    return finalAppointmentDTO.getTime();
                }

                @Override
                public String getStatus() {
                    return finalAppointmentDTO.getStatus();
                }

                @Override
                public Integer getStatusId() {
                    return finalAppointmentDTO.getStatusId();
                }

                @Override
                public Integer getRelativeId() {
                    return null;
                }

                @Override
                public Integer getAmount() {
                    return null;
                }

                @Override
                public Byte getIsShare() {
                    return null;
                }

                @Override
                public Byte getIsAdd() {
                    return null;
                }

                @Override
                public Boolean getIsFeedback() {
                    var feedback = feedbackRepository.findFeedbackByAppointmentId(finalAppointmentDTO.getId());
                    return feedback != null;
                }

                @Override
                public String getCancelUserName() {
                    return null;
                }

                @Override
                public String getCancelDescription() {
                    return null;
                }

                @Override
                public String getCancelReason() {
                    return null;
                }

                @Override
                public String getPatientName() {
                    if (finalRelative != null) {
                        return finalRelative.getFullName();
                    }
                    return finalAppointmentDTO.getPatientName();
                }

                @Override
                public String getPatientImageUrl() {
                    if (finalRelative != null) {
                        return finalRelative.getImageUrl();
                    }

                    return finalAppointmentDTO.getPatientImageUrl();
                }

                @Override
                public Byte getPatientGender() {
                    return null;
                }

                @Override
                public String getPatientPhone() {
                    return finalAppointmentDTO.getPatientPhone();
                }

                @Override
                public Date getPatientDob() {
                    return null;
                }

                @Override
                public String getPatientEthnic() {
                    return null;
                }

                @Override
                public String getPatientEmail() {
                    return null;
                }

                @Override
                public String getPatientAddress() {
                    return null;
                }

                @Override
                public String getDoctorName() {
                    return doctorDTOInf.getFullName();
                }

                @Override
                public String getDoctorImageUrl() {
                    return null;
                }

                @Override
                public Byte getDoctorGender() {
                    return null;
                }

                @Override
                public String getDoctorSpecialty() {
                    return finalAppointmentDTO.getDoctorSpecialty();
                }

                @Override
                public String getDoctorPhone() {
                    return doctorDTOInf.getPhone();
                }

                @Override
                public String getDoctorEmail() {
                    return null;
                }

                @Override
                public String getDoctorJobPlace() {
                    return null;
                }

                @Override
                public String getMedicalRecordUrl() {
                    return null;
                }

                @Override
                public String getPrescriptionUrl() {
                    return null;
                }
            };
            returnAppointmentList.add(appointmentDTO);
        }
        return returnAppointmentList;
    }

    @Override
    public AppointmentDTOInf findAppointmentById(int id) {
        AppointmentDTOInf appointmentDTO = appointmentRepository.findAppointmentDetailById(id);
        if (appointmentDTO == null) {
            throw new NotFoundException("Không tìm thấy cuộc hẹn");
        }
        return setReturnAppointment(appointmentDTO);
    }

    @Override
    public void createNewAppointment(Appointment appointment, String description, String time, List<Integer> medicalRecordId) throws FirebaseMessagingException {


        DoctorDTOInf doctor = doctorService.findDoctorById(appointment.getDoctorId());

        if (doctor.getIsActive() != Constants.IS_ACTIVE) {
            throw new BadRequestException("Bác sĩ hiện đang được xem xét, không thể đặt được");
        }
        int countPending = appointmentRepository.countAppointmentPendingPaymentByPatientId(appointment.getPatientId());
        if (countPending >= 3) {
            throw new BadRequestException("Bạn hãy hoàn thành thanh toán các lần trước để tiếp tục sử dụng. ");
        }
        int countExistingAppointment = appointmentRepository.countExistingAppointment(appointment.getDoctorId(), time, appointment.getScheduleId());
        if (countExistingAppointment >= 1) {
            throw new BadRequestException("Đã có người đặt cuộc hẹn này.");
        }

        Appointment newAppointment = new Appointment();
        if (appointment.getRelativeId() != null && appointment.getRelativeId() != 0) {
            newAppointment.setRelativeId(appointment.getRelativeId());
        }

        newAppointment.setPatientId(appointment.getPatientId());
        newAppointment.setDoctorId(appointment.getDoctorId());
        newAppointment.setScheduleId(appointment.getScheduleId());
        newAppointment.setPaymentStatusId(PaymentStatus.PENDING.status);
        newAppointment.setIsShareMedicalRecord(appointment.getIsShareMedicalRecord());
        newAppointment.setIsAddMedicalRecord((byte) 1);
        newAppointment.setIsSendFcmUpcoming((byte) 0);
        newAppointment.setIsSendFcmOntime((byte) 0);

        AppointmentDetails appointmentDetails = new AppointmentDetails();
        appointmentDetails.setStatusId(AppointmentStatus.NOT_CONFIRM.status);
        appointmentDetails.setDescription(description);
        appointmentDetails.setAmount(listedPriceRepository.getInUseListedPrice().getValue());

        //format String time to date
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = simpleDateFormat.parse(time);
            java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
            appointmentDetails.setTime(sqlDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (appointment.getIsShareMedicalRecord() == Constants.IS_SHARE_MED) {
            List<MedicalRecord> medicalRecordList = new ArrayList<>();
            medicalRecordId.forEach(e -> {
                MedicalRecord medicalRecord = medicalRecordRepository.findById(e).
                        orElseThrow(() -> new NotFoundException("Not found medicalRecord"));
                medicalRecordList.add(medicalRecord);
            });
            newAppointment.setMedicalRecords(medicalRecordList);
        }

        //save to database
        appointmentDetails.setAppointment(newAppointment);
        appointmentRepository.save(newAppointment);
        appointmentDetailRepository.save(appointmentDetails);


//        send notification
        try {
            Date notificationDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");


            //get schedule
            Schedule schedule = scheduleRepository.findById(appointment.getScheduleId())
                    .orElseThrow(() -> new NotFoundException("Not found schedule"));
            String startAt = schedule.getStartAt().toString();
            startAt = startAt.substring(0, startAt.length() - 3);
            String endAt = schedule.getEndAt().toString();
            endAt = endAt.substring(0, endAt.length() - 3);

            //notification for patient
            notificationService.sendNotification(appointment.getPatientId(), "Bạn đã đặt lịch thành công bác sĩ "
                    + doctor.getFullName() + " vào lúc " + startAt + " - " + endAt + " ngày " + simpleDateFormat.format(notificationDate));
            //notification for doctor
            notificationService.sendNotification(appointment.getDoctorId(), "Bạn đã được một bệnh nhân đặt lịch" +
                    " vào lúc " + startAt + " - " + endAt + " ngày " + simpleDateFormat.format(notificationDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<String> registrationTokens = Arrays.asList(
                userRepository.getFcmTokenByUser(appointment.getDoctorId())
        );
        notificationService.sendCloudMessaging(registrationTokens, "Thông báo", Constants.NEW_APPOINTMENT_MESSAGE);
    }

    @Override
    public List<Integer> listScheduleFindByDoctorAndTime(int doctorId, int patientId, String time) {
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        cld.add(Calendar.HOUR, 1);
        String currentTime = formatterTime.format(cld.getTime());
        String currentDate = formatterDate.format(cld.getTime());
        return appointmentRepository.listScheduleFindByDoctorAndTime(doctorId, patientId, time,currentTime,currentDate);
    }

    @Override
    public List<CancelDTOInf> getListCancel() {
        return cancelAppointmentRepository.getListCancel();
    }

    @Override
    public Integer countCancelAppointmentInOneWeek(int userId) {
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = formatter.format(cld.getTime());
        return appointmentRepository.countCancelAppointmentInOneWeek(userId, date);
    }

    @Override
    public Integer countAppointmentPendingPaymentByPatientId(int userId) {
        return appointmentRepository.countAppointmentPendingPaymentByPatientId(userId);
    }

    @Override
    public void cancelAppointment(CancelAppointment cancelAppointment, int userId) {


        AppointmentDetails appointmentDetails = appointmentDetailRepository.findById(cancelAppointment.getAppointmentId())
                .orElseThrow(() -> new NotFoundException("Not found appointment"));
        Appointment appointment = appointmentRepository.findById(cancelAppointment.getAppointmentId())
                .orElseThrow(() -> new NotFoundException("Not found appointment"));

        if (appointmentDetails != null) {
            appointmentDetails.setStatusId(AppointmentStatus.CANCEL.status);
            appointmentDetails.setAppointment(appointment);
            appointmentDetailRepository.save(appointmentDetails);
        }

        cancelAppointment.setUserId(userId);
        cancelAppointmentRepository.save(cancelAppointment);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = formatter.format(cld.getTime());
        if (appointmentRepository.countCancelAppointmentInOneWeek(userId, date) >= 3) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng"));
            user.setIsActive((byte) Constants.IS_BAN);
            user.setReason("Huỷ quá 3 lần trong 1 tuần");
            userRepository.save(user);
        }

        try {
            Date notificationDate = new SimpleDateFormat("yyyy-MM-dd").parse(appointmentDetails.getTime().toString());
            Schedule schedule = scheduleRepository.findById(appointment.getScheduleId())
                    .orElseThrow(() -> new NotFoundException("Not found schedule"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String startAt = schedule.getStartAt().toString();
            startAt = startAt.substring(0, startAt.length() - 3);
            String endAt = schedule.getEndAt().toString();
            endAt = endAt.substring(0, endAt.length() - 3);

            //notification for patient
            String notificationMessage = "Lịch khám của bạn vào lúc " + startAt + " - " + endAt + " ngày "
                    + simpleDateFormat.format(notificationDate) + " đã bị huỷ.";

            notificationService.sendNotification(appointment.getPatientId(), notificationMessage);

            //notification for doctor
            notificationService.sendNotification(appointment.getDoctorId(), notificationMessage);

            List<String> registrationTokens = Arrays.asList(
                    userRepository.getFcmTokenByUser(appointment.getPatientId()),
                    userRepository.getFcmTokenByUser(appointment.getDoctorId())
            );
            notificationService.sendCloudMessaging(registrationTokens, "Thông báo", notificationMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void confirmAppointment(AppointmentDetails appointmentDetails, int id) throws FirebaseMessagingException {
        AppointmentDetails appointmentDs = appointmentDetailRepository.findAppointmentDetailsByAppointmentId(id);
        appointmentDs.setStatusId(appointmentDetails.getStatusId());
        appointmentDetailRepository.save(appointmentDs);

        //send notification
        Appointment appointment = appointmentRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Not found appointment"));
        appointmentRepository.save(appointment);


        try {
            Date notificationDate = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(appointmentDs.getTime().toString());
            Schedule schedule = scheduleRepository.findById(appointment.getScheduleId())
                    .orElseThrow(() -> new NotFoundException("Not found schedule"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String startAt = schedule.getStartAt().toString();
            startAt = startAt.substring(0, startAt.length() - 3);
            String endAt = schedule.getEndAt().toString();
            endAt = endAt.substring(0, endAt.length() - 3);

            String notificationMessage = "Lịch khám của bạn vào lúc " + startAt + " - " + endAt + " ngày "
                    + simpleDateFormat.format(notificationDate) + " đã được bác sĩ xác nhận.";
            //notification for patient
            notificationService.sendNotification(appointment.getPatientId(),
                    notificationMessage);


            List<String> registrationTokens = Arrays.asList(
                    userRepository.getFcmTokenByUser(appointment.getPatientId())
            );
            notificationService.sendCloudMessaging(registrationTokens, "Thông báo", notificationMessage);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void endAppointment(int id) {
        AppointmentDetails appointmentDs = appointmentDetailRepository.findAppointmentDetailsByAppointmentId(id);
        appointmentDs.setStatusId(AppointmentStatus.COMPLETE.status);
        appointmentDetailRepository.save(appointmentDs);

        //send notification
        Appointment appointment = appointmentRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Not found appointment"));
        appointmentRepository.save(appointment);

        try {
            Date notificationDate = new SimpleDateFormat("yyyy-MM-dd").parse(appointmentDs.getTime().toString());
            Schedule schedule = scheduleRepository.findById(appointment.getScheduleId())
                    .orElseThrow(() -> new NotFoundException("Not found schedule"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String startAt = schedule.getStartAt().toString();
            startAt = startAt.substring(0, startAt.length() - 3);
            String endAt = schedule.getEndAt().toString();
            endAt = endAt.substring(0, endAt.length() - 3);

            //notification for patient
            String notificationMessage="Lịch khám của bạn vào lúc " + startAt + " - " + endAt + " ngày "
                    + simpleDateFormat.format(notificationDate) + " đã được hoàn thành.";
            notificationService.sendNotification(appointment.getPatientId(),notificationMessage);

            //notification for doctor
            notificationService.sendNotification(appointment.getDoctorId(),notificationMessage);

            List<String> registrationTokens = Arrays.asList(
                    userRepository.getFcmTokenByUser(appointment.getPatientId())
            );
            notificationService.sendCloudMessaging(registrationTokens, "Thông báo", notificationMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeRefuseFillReason(int id, String reason) {
        AppointmentDetails appointmentDs = appointmentDetailRepository.findAppointmentDetailsByAppointmentId(id);
        appointmentDs.setRefuseFillReason(reason);
        appointmentDetailRepository.save(appointmentDs);
    }

    @Override
    public void updateIsAddMedicalRecord(int id, boolean isAdd) {
        Appointment appointment = appointmentRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Not found appointment"));
        appointment.setIsAddMedicalRecord(isAdd ? (byte) 0 : 1);
        appointmentRepository.save(appointment);
    }

    @Override
    public AppointmentDTOInf getCurrentAppointmentAvailable(String patientPhone, String doctorPhone) {

        User patient = userRepository.findUserByPhone(patientPhone);
        User doctor = userRepository.findUserByPhone(doctorPhone);
        Date d = new Date();
        DateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss");

        dateFormatDay.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        dateFormatTime.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        String date = dateFormatDay.format(d);
        String time = dateFormatTime.format(d);

        return appointmentRepository.getCurrentAppointmentAvailable(patient.getId(), doctor.getId(), date, time);
    }

    @Override
    public List<AppointmentDTOInf> findAppointmentOverdue() {
        Date d = new Date();
        DateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss");

        dateFormatDay.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        dateFormatTime.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        String date = dateFormatDay.format(d);
        String time = dateFormatTime.format(d);

        return appointmentRepository.findAppointmentOverdue(date, time);
    }

    @Override
    public List<AppointmentDTOInfForAdmin> getAllAppointmentForAdmin(int index, String search) {
        return appointmentRepository.getAllAppointmentForAdmin(index, search);
    }

    @Override
    public AppointmentDTOInfForAdmin getAppointmentDetailForAdmin(int appointmentId) {
        AppointmentDTOInfForAdmin appointment = appointmentRepository.getAppointmentDetailForAdmin(appointmentId);
        if (appointment == null) {
            throw new NotFoundException("Không tìm thấy cuộc hẹn");
        }
        AppointmentDTOInfForAdmin returnAppointment = new AppointmentDTOInfForAdmin() {
            @Override
            public Integer getId() {
                return appointment.getId();
            }

            @Override
            public Integer getPatientId() {
                return appointment.getPatientId();
            }

            @Override
            public String getPatientName() {
                return appointment.getPatientName();
            }

            @Override
            public String getPatientPhone() {
                return appointment.getPatientPhone();
            }

            @Override
            public String getDoctorName() {
                return appointment.getDoctorName();
            }

            @Override
            public Integer getDoctorId() {
                return appointment.getDoctorId();
            }

            @Override
            public String getPrescriptionTrace() {
                return appointment.getPrescriptionTrace();
            }

            @Override
            public String getPrescriptionUrl() {
                return appointment.getPrescriptionUrl();
            }

            @Override
            public String getMedicalRecordTrace() {
                return appointment.getMedicalRecordTrace();
            }

            @Override
            public String getMedicalRecordUrl() {
                return appointment.getMedicalRecordUrl();
            }

            @Override
            public Date getTime() {
                return appointment.getTime();
            }

            @Override
            public String getAppointmentStatus() {
                return appointment.getAppointmentStatus();
            }

            @Override
            public Time getStartAt() {
                return appointment.getStartAt();
            }

            @Override
            public Time getEndAt() {
                return appointment.getEndAt();
            }

            @Override
            public String getPaymentStatus() {
                return appointment.getPaymentStatus();
            }

            @Override
            public String getRelativeName() {
                return appointment.getRelativeName();
            }

            @Override
            public String getCancelReason() {
                return appointment.getCancelReason();
            }

            @Override
            public String getRefuseFillReason() {
                return appointment.getRefuseFillReason();
            }

            @Override
            public String getCancelPerson() {
                return appointment.getCancelPerson();
            }

            @Override
            public List<MedicalRecordDTOInf> getListSharedMedicalRecord() {

                return medicalRecordRepository.getSharedMedicalRecordByAppointmentAdmin(appointmentId);
            }

            @Override
            public List<PrescriptionDTOInf> getListSharedPrescription() {
                return prescriptionRepository.getSharedPrescriptionByAppointmentAdmin(appointmentId);
            }
        };

        return returnAppointment;
    }

    @Override
    public int getNumberOfAppointmentForAdmin(String search) {
        return appointmentRepository.getNumberOfAppointmentForAdmin(search);
    }

    @Override
    public List<AppointmentDTOInf> findDoneAppointment(int userId, int paymentStatusId, boolean isPatient) {
        List<AppointmentDTOInf> appointmentList = appointmentRepository.findDoneAppointment(userId, paymentStatusId, isPatient);
        List<AppointmentDTOInf> returnAppointmentList = new ArrayList<>();
        for (AppointmentDTOInf appointmentDTO : appointmentList) {

            AppointmentDTOInf finalAppointmentDTO = appointmentDTO;
            PatientDTOInf patient = patientService.findPatientById(finalAppointmentDTO.getPatientId());
            appointmentDTO = new AppointmentDTOInf() {
                @Override
                public Integer getId() {
                    return finalAppointmentDTO.getId();
                }

                @Override
                public Integer getDoctorId() {
                    return null;
                }

                @Override
                public Integer getPatientId() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return finalAppointmentDTO.getDescription();
                }

                @Override
                public Time getStartAt() {
                    return finalAppointmentDTO.getStartAt();
                }

                @Override
                public Time getEndAt() {
                    return finalAppointmentDTO.getEndAt();
                }

                @Override
                public String getTime() {
                    return finalAppointmentDTO.getTime();
                }

                @Override
                public String getStatus() {
                    return finalAppointmentDTO.getStatus();
                }

                @Override
                public Integer getStatusId() {
                    return finalAppointmentDTO.getStatusId();
                }

                @Override
                public Integer getRelativeId() {
                    return null;
                }

                @Override
                public Integer getAmount() {
                    return finalAppointmentDTO.getAmount();
                }

                @Override
                public Byte getIsShare() {
                    return null;
                }

                @Override
                public Byte getIsAdd() {
                    return null;
                }

                @Override
                public Boolean getIsFeedback() {
                    var feedback = feedbackRepository.findFeedbackByAppointmentId(finalAppointmentDTO.getId());
                    return feedback != null;
                }

                @Override
                public String getCancelUserName() {
                    return null;
                }

                @Override
                public String getCancelDescription() {
                    return null;
                }

                @Override
                public String getCancelReason() {
                    return null;
                }

                @Override
                public String getPatientName() {
                    return patient.getFullName();
                }

                @Override
                public String getPatientImageUrl() {
                    return null;
                }

                @Override
                public Byte getPatientGender() {
                    return null;
                }

                @Override
                public String getPatientPhone() {
                    return patient.getPhone();
                }

                @Override
                public Date getPatientDob() {
                    return null;
                }

                @Override
                public String getPatientEthnic() {
                    return null;
                }

                @Override
                public String getPatientEmail() {
                    return null;
                }

                @Override
                public String getPatientAddress() {
                    return null;
                }

                @Override
                public String getDoctorName() {
                    return finalAppointmentDTO.getDoctorName();
                }

                @Override
                public String getDoctorImageUrl() {
                    return finalAppointmentDTO.getDoctorImageUrl();
                }

                @Override
                public Byte getDoctorGender() {
                    return null;
                }

                @Override
                public String getDoctorSpecialty() {
                    return finalAppointmentDTO.getDoctorSpecialty();
                }

                @Override
                public String getDoctorPhone() {
                    return finalAppointmentDTO.getDoctorPhone();
                }

                @Override
                public String getDoctorEmail() {
                    return null;
                }

                @Override
                public String getDoctorJobPlace() {
                    return null;
                }

                @Override
                public String getMedicalRecordUrl() {
                    return null;
                }

                @Override
                public String getPrescriptionUrl() {
                    return null;
                }
            };
            returnAppointmentList.add(appointmentDTO);
        }
        return returnAppointmentList;
    }

    @Override
    public List<AppointmentDTOInfForAdmin> getAllAppointmentDetailsForAdmin(int index, String search) {
        return appointmentRepository.getAllAppointmentDetailsForAdmin(index, search);
    }

    @Override
    public int getNumberOfAppointmentDetailsForAdmin(String search) {
        return appointmentRepository.getNumberOfAppointmentDetailsForAdmin(search);
    }

    @Override
    public int getInUseListedPrice() {
        return listedPriceRepository.getInUseListedPrice().getValue();
    }

    @Override
    public List<AppointmentDTOInf> getComingAppointmentInFifteenMin() {
        Date d = new Date();
        DateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss");

        dateFormatDay.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        dateFormatTime.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        String date = dateFormatDay.format(d);
        String time = dateFormatTime.format(d);
        return appointmentRepository.getComingAppointmentInFifteenMin(date, time);
    }

    @Override
    public List<AppointmentDTOInf> getCurrentAppointmentOnTime() {
        Date d = new Date();
        DateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss");

        dateFormatDay.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        dateFormatTime.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        String date = dateFormatDay.format(d);
        String time = dateFormatTime.format(d);
        return appointmentRepository.getCurrentAppointmentOnTime(date, time);
    }

    private AppointmentDTOInf setReturnAppointment(AppointmentDTOInf appointmentDTO) {
        PatientDTOInf patient = patientService.findPatientById(appointmentDTO.getPatientId());
        DoctorDTOInf doctor = doctorService.findDoctorById(appointmentDTO.getDoctorId());
        CancelReasonDTOInf cancelReasonDTOInf = cancelAppointmentRepository.getCancelDetailByAppointment(appointmentDTO.getId());
        Relative relative = null;
        if (appointmentDTO.getRelativeId() != null) {
            relative = relativeService.findRelativeById(appointmentDTO.getRelativeId());
        }
        Relative finalRelative = relative;
        AppointmentDTOInf returnAppointment = new AppointmentDTOInf() {
            @Override
            public Integer getId() {
                return appointmentDTO.getId();
            }

            @Override
            public Integer getDoctorId() {
                return appointmentDTO.getDoctorId();
            }

            @Override
            public Integer getPatientId() {
                return appointmentDTO.getPatientId();
            }

            @Override
            public String getDescription() {
                return appointmentDTO.getDescription();
            }

            @Override
            public Time getStartAt() {
                return appointmentDTO.getStartAt();
            }

            @Override
            public Time getEndAt() {
                return appointmentDTO.getEndAt();
            }


            @Override
            public String getTime() {
                return appointmentDTO.getTime();
            }

            @Override
            public String getStatus() {
                return appointmentDTO.getStatus();
            }

            @Override
            public Integer getStatusId() {
                return appointmentDTO.getStatusId();
            }

            @Override
            public Integer getRelativeId() {
                return appointmentDTO.getRelativeId();
            }

            @Override
            public Integer getAmount() {
                return appointmentDTO.getAmount();
            }

            @Override
            public Byte getIsShare() {
                return appointmentDTO.getIsShare();
            }

            @Override
            public Byte getIsAdd() {
                return appointmentDTO.getIsAdd();
            }

            @Override
            public Boolean getIsFeedback() {
                var feedback = feedbackRepository.findFeedbackByAppointmentId(appointmentDTO.getId());
                return feedback != null;
            }

            @Override
            public String getCancelUserName() {
                if (cancelReasonDTOInf != null) {
                    return cancelReasonDTOInf.getCancelUser();
                }
                return null;
            }

            @Override
            public String getCancelDescription() {
                if (cancelReasonDTOInf != null) {
                    return cancelReasonDTOInf.getCancelDescription();
                }
                return null;
            }

            @Override
            public String getCancelReason() {
                if (cancelReasonDTOInf != null) {
                    return cancelReasonDTOInf.getCancelReason();
                }
                return null;
            }

            @Override
            public String getPatientName() {
                if (finalRelative != null) {
                    return finalRelative.getFullName();
                }
                return patient.getFullName();
            }

            @Override
            public String getPatientImageUrl() {
                return null;
            }

            @Override
            public Byte getPatientGender() {
                if (finalRelative != null) {
                    return finalRelative.getGender();
                }
                return patient.getGender();
            }

            @Override
            public String getPatientPhone() {
                if (finalRelative != null) {
                    return finalRelative.getPhone();
                }
                return patient.getPhone();
            }

            @Override
            public Date getPatientDob() {
                if (finalRelative != null) {
                    return finalRelative.getDateOfBirth();
                }
                return patient.getDob();
            }

            @Override
            public String getPatientEthnic() {
                if (finalRelative != null) {
                    return ethnicService.findEthnicById(finalRelative.getEthnicId()).getName();
                }
                return patient.getEthnicId() == null ? null : ethnicService.findEthnicById(patient.getEthnicId()).getName();
            }

            @Override
            public String getPatientEmail() {
                if (finalRelative != null) {
                    return finalRelative.getEmail();
                }
                return patient.getEmail();
            }

            @Override
            public String getPatientAddress() {
                Address address = addressService.findAddressById(patient.getAddressId());
                return address.getStreetName();
            }

            @Override
            public String getDoctorName() {
                return doctor.getFullName();
            }

            @Override
            public String getDoctorImageUrl() {
                return null;
            }

            @Override
            public Byte getDoctorGender() {
                return doctor.getGender();
            }

            @Override
            public String getDoctorSpecialty() {
                return doctor.getSpecialty();
            }

            @Override
            public String getDoctorPhone() {
                return doctor.getPhone();
            }

            @Override
            public String getDoctorEmail() {
                return doctor.getEmail();
            }

            @Override
            public String getDoctorJobPlace() {
                return doctor.getJobPlace();
            }

            @Override
            public String getMedicalRecordUrl() {
                return appointmentDTO.getMedicalRecordUrl();
            }

            @Override
            public String getPrescriptionUrl() {
                return appointmentDTO.getPrescriptionUrl();
            }
        };
        return returnAppointment;
    }
}
