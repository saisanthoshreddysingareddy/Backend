package com.example.splitwise.controllers;


import com.example.splitwise.dtos.ResponseStatus;
import com.example.splitwise.dtos.SettleGroupRequestDto;
import com.example.splitwise.dtos.SettleGroupResponseDto;
import com.example.splitwise.dtos.SettleUserRequestDto;
import com.example.splitwise.dtos.SettleUserResponseDto;

import com.example.splitwise.exceptions.InvalidGroupException;
import com.example.splitwise.exceptions.InvalidUserException;
import com.example.splitwise.services.SettleUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SettleUpController {
    @Autowired
    private SettleUpService settleUpService;

    public SettleGroupResponseDto settleGroup(SettleGroupRequestDto dto){

        SettleGroupResponseDto response = new SettleGroupResponseDto();

        try{
            response.setTransactions(
                    settleUpService.settleGroup(dto.getGroupId())
            );
            response.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (InvalidGroupException e){
            response.setResponseStatus(ResponseStatus.FAILURE);
        }

        return response;
    }

    public SettleUserResponseDto settleUser(SettleUserRequestDto requestDto){

        SettleUserResponseDto response = new SettleUserResponseDto();

        try{
            response.setTransactions(settleUpService.settleUser(requestDto.getUserId()));
            response.setResponseStatus(ResponseStatus.SUCCESS);
        }catch (InvalidUserException e){
            response.setResponseStatus(ResponseStatus.FAILURE);
        }

        return response;
    }
}
