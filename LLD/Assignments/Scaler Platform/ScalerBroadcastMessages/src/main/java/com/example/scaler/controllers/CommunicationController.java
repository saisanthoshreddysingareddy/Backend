package com.example.scaler.controllers;

import org.springframework.stereotype.Controller;

import com.example.scaler.dtos.BroadcastMessageRequestDto;
import com.example.scaler.dtos.BroadcastMessageResponseDto;
import com.example.scaler.dtos.ResponseStatus;
import com.example.scaler.models.Communication;
import com.example.scaler.services.CommunicationService;

@Controller
public class CommunicationController {

    CommunicationService communicationService;
    public CommunicationController(CommunicationService communicationService){
        this.communicationService = communicationService;
    }

    public BroadcastMessageResponseDto broadcastMessage(BroadcastMessageRequestDto requestDto) {
        BroadcastMessageResponseDto responseDto = new BroadcastMessageResponseDto();
        try{
            Communication communication = communicationService.broadcastMessage(requestDto.getBatchId(), requestDto.getUserId(), requestDto.getMessage());
            responseDto.setCommunication(communication);
            responseDto.setStatus(ResponseStatus.SUCCESS);
            
        }catch(Exception e){
            responseDto.setStatus(ResponseStatus.FAILURE);

        }
        
        return responseDto;
    }
}
