package com.example.telecare.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Getter
@Setter
public class Schedule {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Basic
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "start_at")
    private Time startAt;
    @Basic
    @Column(name = "end_at")
    private Time endAt;
    @Basic
    @Column(name = "shift")
    private String shift;


}
