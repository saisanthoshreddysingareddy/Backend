package com.example.qcommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class PartnerTaskMapping extends BaseModel{
    @ManyToOne
    private Partner partner;
    @ManyToOne
    private Task task;
}
