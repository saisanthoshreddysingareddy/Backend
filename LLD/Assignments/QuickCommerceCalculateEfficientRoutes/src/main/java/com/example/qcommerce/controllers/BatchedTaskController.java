package com.example.qcommerce.controllers;

import com.example.qcommerce.dtos.BuildBatchedTaskRouteRequestDto;
import com.example.qcommerce.dtos.BuildBatchedTaskRouteResponseDto;
import com.example.qcommerce.dtos.ResponseStatus;
import com.example.qcommerce.models.Location;
import com.example.qcommerce.services.BatchedTaskService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BatchedTaskController {
    BatchedTaskService batchedTaskService;

    // Constructor
    public BatchedTaskController(BatchedTaskService batchedTaskService){
        this.batchedTaskService = batchedTaskService;
    }

    public BuildBatchedTaskRouteResponseDto buildRoute(BuildBatchedTaskRouteRequestDto requestDto){
        BuildBatchedTaskRouteResponseDto responseDto = new BuildBatchedTaskRouteResponseDto();
        try{
            List<Location> route = batchedTaskService.buildRoute(requestDto.getBatchedTaskId());
            responseDto.setRouteToBeTaken(route);
            responseDto.setStatus(ResponseStatus.SUCCESS);
        }catch (Exception e){
            responseDto.setStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}
