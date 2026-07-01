package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "group_admins")
@Getter
@Setter
public class GroupAdmin extends BaseModel {

    @ManyToOne
    private Group group;

    @ManyToOne
    private User admin;

    @ManyToOne
    private User addedBy;
}