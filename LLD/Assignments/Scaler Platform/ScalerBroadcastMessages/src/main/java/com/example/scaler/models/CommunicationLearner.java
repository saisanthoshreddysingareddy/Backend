package com.example.scaler.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class CommunicationLearner extends BaseModel {

    @ManyToOne
    private Learner learner;

    @ManyToOne
    private Communication communication;

    private boolean whatsappDelivered;

    private boolean emailDelivered;
}