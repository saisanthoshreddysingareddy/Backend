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
public class BatchLearner extends BaseModel {

    @ManyToOne
    private Batch batch;

    @ManyToOne
    private Learner learner;

    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date exitDate;
}
