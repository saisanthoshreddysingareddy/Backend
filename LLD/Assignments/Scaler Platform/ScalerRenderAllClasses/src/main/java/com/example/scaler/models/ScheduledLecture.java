package com.example.scaler.models;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ScheduledLecture extends BaseModel {

    @ManyToOne
    private Lecture lecture;

    @ManyToOne
    private Batch batch;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lectureStartTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lectureEndTime;

    private String lectureLink;
}
