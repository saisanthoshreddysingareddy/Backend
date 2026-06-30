package com.example.scaler.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.scaler.dtos.FetchTimelineRequestDto;
import com.example.scaler.dtos.FetchTimelineResponseDto;
import com.example.scaler.dtos.ResponseStatus;
import com.example.scaler.models.ScheduledLecture;
import com.example.scaler.services.LearnerService;

@Controller
public class LearnerController {

    @Autowired
    private LearnerService learnerService;

    public FetchTimelineResponseDto fetchTimeline(FetchTimelineRequestDto requestDto){
        FetchTimelineResponseDto responseDto = new FetchTimelineResponseDto();
        try {
            List<ScheduledLecture> scheduledLecture = learnerService.fetchTimeline(requestDto.getLearnerId());
            responseDto.setLectures(scheduledLecture);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
