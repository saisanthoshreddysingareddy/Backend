package com.example.scaler.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class ScheduledLecture extends BaseModel{
    @OneToOne
    private Lecture lecture;
    @ManyToOne
    private Batch batch;
    @ManyToOne
    private Instructor instructor;
    private Date lectureStartTime;
    private Date lectureEndTime;
    private String lectureLink;
}
