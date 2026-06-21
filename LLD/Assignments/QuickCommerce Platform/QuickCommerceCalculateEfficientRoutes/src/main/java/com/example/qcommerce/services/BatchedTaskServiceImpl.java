package com.example.qcommerce.services;

import com.example.qcommerce.adapters.MapsAdapter;
import com.example.qcommerce.exceptions.BatchedTaskNotFoundException;
import com.example.qcommerce.models.BatchedTask;
import com.example.qcommerce.models.Location;
import com.example.qcommerce.models.Task;
import com.example.qcommerce.repositories.BatchedTaskRepository;
import com.example.qcommerce.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BatchedTaskServiceImpl implements  BatchedTaskService{
    BatchedTaskRepository batchedTaskRepository;
    TaskRepository taskRepository;
    MapsAdapter mapsAdapter;

    // Constructor
    public BatchedTaskServiceImpl(BatchedTaskRepository batchedTaskRepository,
                                  TaskRepository taskRepository,
                                  MapsAdapter mapsAdapter){
        this.batchedTaskRepository = batchedTaskRepository;
        this.taskRepository = taskRepository;
        this.mapsAdapter = mapsAdapter;
    }

    // Interface method
    public List<Location> buildRoute(long batchedTaskId) throws BatchedTaskNotFoundException, Exception{
        // Check given batchedTaskId Existence
        Optional<BatchedTask> optionalBatchedTask = batchedTaskRepository.findById(batchedTaskId);
        if(optionalBatchedTask.isEmpty()){
            throw new BatchedTaskNotFoundException("Batched task not found");
        }
        BatchedTask batchedTask = optionalBatchedTask.get();
        List<Task> tasksList = batchedTask.getTasks();

        // Build current route arraylist
        List<Location> currentRoute = new ArrayList<>();
        if(tasksList != null){
            for(Task task : tasksList){
                if(task != null && task.getDropLocation() != null){
                    currentRoute.add(task.getDropLocation());
                }
            }
        }


        if(currentRoute.isEmpty()){
            throw new Exception("No task locations found");
        }

        // Calculate route from Google Maps
        List<Location> route = mapsAdapter.buildRoute(currentRoute);
        if(route.isEmpty()){
            throw new Exception("No route found");
        }

        return route;
    }


}
