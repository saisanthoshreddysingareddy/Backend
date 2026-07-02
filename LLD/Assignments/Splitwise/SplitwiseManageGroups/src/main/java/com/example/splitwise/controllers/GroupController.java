package com.example.splitwise.controllers;

import com.example.splitwise.dtos.*;
import com.example.splitwise.exceptions.InvalidGroupException;
import com.example.splitwise.exceptions.InvalidUserException;
import com.example.splitwise.exceptions.UnAuthorizedAccessException;
import com.example.splitwise.models.Group;
import com.example.splitwise.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    public CreateGroupResponseDto createGroup(CreateGroupRequestDto requestDto){

        CreateGroupResponseDto responseDto = new CreateGroupResponseDto();

        try{
            Group group = groupService.createGroup(
                    requestDto.getName(),
                    requestDto.getDescription(),
                    requestDto.getCreatorUserId()
            );

            responseDto.setGroup(group);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        }catch (InvalidUserException e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }

    public DeleteGroupResponseDto deleteGroup(DeleteGroupRequestDto requestDto){

        DeleteGroupResponseDto responseDto = new DeleteGroupResponseDto();

        try{

            groupService.deleteGroup(
                    requestDto.getGroupId(),
                    requestDto.getUserId()
            );

            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        }catch (InvalidGroupException |
                InvalidUserException |
                UnAuthorizedAccessException e){

            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}