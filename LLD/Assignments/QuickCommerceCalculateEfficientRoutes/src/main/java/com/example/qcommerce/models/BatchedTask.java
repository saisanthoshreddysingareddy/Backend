package com.example.qcommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import jakarta.persistence.FetchType; 
import java.util.Date;
import java.util.List;

@Entity
@Data
public class BatchedTask extends BaseModel {
    @OneToMany(fetch = FetchType.EAGER)
    private List<Task> tasks;

    private Date batchedAt;
}
