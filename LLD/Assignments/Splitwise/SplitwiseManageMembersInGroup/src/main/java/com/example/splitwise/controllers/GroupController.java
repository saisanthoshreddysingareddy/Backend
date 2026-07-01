package com.example.splitwise.controllers;

import com.example.splitwise.dtos.*;
import com.example.splitwise.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    public AddMemberResponseDto addMember(AddMemberRequestDto requestDto) {

        AddMemberResponseDto responseDto = new AddMemberResponseDto();

        try {
            responseDto.setGroupMember(
                    groupService.addMember(
                            requestDto.getGroupId(),
                            requestDto.getAdminId(),
                            requestDto.getMemberId()
                    )
            );

            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }

    public RemoveMemberResponseDto removeMember(RemoveMemberRequestDto requestDto) {

        RemoveMemberResponseDto responseDto = new RemoveMemberResponseDto();

        try {

            groupService.removeMember(
                    requestDto.getGroupId(),
                    requestDto.getAdminId(),
                    requestDto.getMemberId()
            );

            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        } catch (Exception e) {

            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }

    public FetchMembersResponseDto fetchMembers(FetchMembersRequestDto requestDto) {

        FetchMembersResponseDto responseDto = new FetchMembersResponseDto();

        try {

            responseDto.setMembers(
                    groupService.fetchAllMembers(
                            requestDto.getGroupId(),
                            requestDto.getMemberId()
                    )
            );

            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        } catch (Exception e) {

            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}