package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "group_members")
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