package com.example.qcommerce.services;

import com.example.qcommerce.exceptions.PartnerMissingException;
import com.example.qcommerce.exceptions.TaskMissingException;
import com.example.qcommerce.models.Location;
import com.example.qcommerce.models.Partner;
import com.example.qcommerce.models.PartnerTaskMapping;
import com.example.qcommerce.models.Task;
import com.example.qcommerce.repositories.PartnerRepository;
import com.example.qcommerce.repositories.TaskRepository;
import com.example.qcommerce.strategies.PartnerMatchingStrategy;
import com.example.qcommerce.utils.DistanceUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchPartnerTaskServiceImpl implements MatchPartnerTaskService{
    TaskRepository taskRepository;
    PartnerRepository partnerRepository;
    DistanceUtils distanceUtils;
    PartnerMatchingStrategy partnerMatchingStrategy;

    // Constructor
    public MatchPartnerTaskServiceImpl(TaskRepository taskRepository,
                                       PartnerRepository partnerRepository,
                                       PartnerMatchingStrategy partnerMatchingStrategy){
        this.taskRepository = taskRepository;
        this.partnerRepository = partnerRepository;
        this.partnerMatchingStrategy = partnerMatchingStrategy;
    }

    // Interface Method
    public List<PartnerTaskMapping> matchPartnersAndTasks(List<Long> partnerIds, List<Long> taskIds){

        // Check given partners are empty or not
        if(partnerIds.size() == 0){
            throw new PartnerMissingException("No Partners found");
        }
        // Get available partners
        List<Partner> partnersList = partnerRepository.findAllById(partnerIds);
        if(partnersList.size() != partnerIds.size()){
            throw new PartnerMissingException("One or more partners are missing");
        }

        // Check tasks are empty or not
        if(taskIds.size() == 0){
            throw new TaskMissingException("No tasks found");
        }
        // Get available tasks
        List<Task> tasksList = taskRepository.findAllById(taskIds);
        if(tasksList.size() != taskIds.size()){
            throw new TaskMissingException("One or more tasks are missing");
        }

        List<PartnerTaskMapping> partnerTaskMappingsList=  partnerMatchingStrategy.match(partnersList, tasksList);

        // Out of Max value case
        if(partnerTaskMappingsList.size() == 0){
            throw new PartnerMissingException("No partners available at this location");
        }



        return partnerTaskMappingsList;
    }
}
