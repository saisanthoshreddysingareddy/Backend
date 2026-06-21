package com.example.ecom.controllers;

import org.springframework.stereotype.Controller;

import com.example.ecom.dtos.CreateOrUpdateRequestDto;
import com.example.ecom.dtos.CreateOrUpdateResponseDto;
import com.example.ecom.dtos.DeleteInventoryRequestDto;
import com.example.ecom.dtos.DeleteInventoryResponseDto;
import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.exceptions.UnAuthorizedAccessException;
import com.example.ecom.exceptions.UserNotFoundException;
import com.example.ecom.models.Inventory;
import com.example.ecom.services.InventoryService;

@Controller
public class InventoryController {

    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public CreateOrUpdateResponseDto createOrUpdateInventory(CreateOrUpdateRequestDto requestDto){
        CreateOrUpdateResponseDto responseDto = new CreateOrUpdateResponseDto();
        try{
            Inventory inventory = inventoryService.createOrUpdateInventory(requestDto.getUserId(), requestDto.getProductId(), requestDto.getQuantity());
            responseDto.setInventory(inventory);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch (UserNotFoundException  | ProductNotFoundException | UnAuthorizedAccessException e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }

    public DeleteInventoryResponseDto deleteInventory(DeleteInventoryRequestDto requestDto){
        DeleteInventoryResponseDto responseDto = new DeleteInventoryResponseDto();
        try{
            inventoryService.deleteInventory(requestDto.getUserId(), requestDto.getProductId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch(UserNotFoundException | UnAuthorizedAccessException e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);

        }
        return responseDto;
    }


}
