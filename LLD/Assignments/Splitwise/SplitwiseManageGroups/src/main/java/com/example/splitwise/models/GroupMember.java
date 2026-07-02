package com.example.splitwise.models;


import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GroupMember extends BaseModel {

    @ManyToOne
    private Group group;

    @ManyToOne
    private User user;

    @ManyToOne
    private User addedBy;

    private Date addedAt;
}