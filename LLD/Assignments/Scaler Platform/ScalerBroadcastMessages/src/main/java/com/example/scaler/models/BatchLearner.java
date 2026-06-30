package com.example.scaler.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class BatchLearner extends BaseModel {

    @ManyToOne
    private Batch batch;

    @ManyToOne
    private Learner learner;

    private Date entryDate;

    private Date exitDate;
}