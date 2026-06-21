package com.example.qcommerce.controllers;

import com.example.qcommerce.dtos.MatchPartnerTaskRequestDto;
import com.example.qcommerce.dtos.MatchPartnerTaskResponseDto;
import com.example.qcommerce.dtos.ResponseStatus;
import com.example.qcommerce.models.PartnerTaskMapping;
import com.example.qcommerce.services.MatchPartnerTaskService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MatchPartnerTaskController {
    MatchPartnerTaskService matchPartnerTaskService;

    // Constructor
    public MatchPartnerTaskController(MatchPartnerTaskService matchPartnerTaskService){
        this.matchPartnerTaskService = matchPartnerTaskService;
    }

    public MatchPartnerTaskResponseDto matchPartnersAndTasks(MatchPartnerTaskRequestDto requestDto){
        MatchPartnerTaskResponseDto responseDto = new MatchPartnerTaskResponseDto();
        try{
            List<PartnerTaskMapping> results = matchPartnerTaskService.matchPartnersAndTasks(requestDto.getPartnerIds(),requestDto.getTaskIds());
            responseDto.setPartnerTaskMappings(results);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}
