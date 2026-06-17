package com.example.bmsbookticket.models;

import lombok.Data;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.cdi.Eager;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
@Data
public class Show extends BaseModel{
    private Date startTime;
    private Date endTime;

    private List<Feature> features;
    @ManyToOne
    private Screen screen;
}
