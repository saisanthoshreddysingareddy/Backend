package com.example.bmsbookticket.controllers;

import com.example.bmsbookticket.dtos.*;
import com.example.bmsbookticket.models.User;
import com.example.bmsbookticket.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class UserController {
    UserService userService;

    public SignupUserResponseDTO signupUser(SignupUserRequestDTO requestDTO){
        SignupUserResponseDTO responseDTO = new SignupUserResponseDTO();
        try{
            User user = userService.signupUser(requestDTO.getName(), requestDTO.getEmail(), requestDTO.getPassword());
            responseDTO.setUserId(user.getId());
            responseDTO.setName(user.getName());
            responseDTO.setEmail(user.getEmail());
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        }catch(Exception e){
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }

    public LoginResponseDto login(LoginRequestDto requestDto){
        LoginResponseDto responseDto = new LoginResponseDto();
        try{
            boolean isLoggedIn = userService.login(requestDto.getEmail(), requestDto.getPassword());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setLoggedIn(isLoggedIn);
        }catch(Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }

}
