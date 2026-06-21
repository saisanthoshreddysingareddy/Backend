package com.example.ecom.controllers;

import com.example.ecom.dtos.*;
import com.example.ecom.models.GiftCard;
import com.example.ecom.services.GiftCardService;
import org.springframework.stereotype.Controller;

@Controller
public class GiftCardController {

    GiftCardService giftCardService;
    
    // Constructor
    public GiftCardController(GiftCardService giftCardService){
        this.giftCardService = giftCardService;
    }

    public CreateGiftCardResponseDto createGiftCard(CreateGiftCardRequestDto requestDto){
        CreateGiftCardResponseDto responseDto = new CreateGiftCardResponseDto();
        try{
            GiftCard giftCard = giftCardService.createGiftCard(requestDto.getAmount());
            responseDto.setGiftCard(giftCard);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch (Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        
        return responseDto;
    }

    public RedeemGiftCardResponseDto redeemGiftCard(RedeemGiftCardRequestDto requestDto){
        RedeemGiftCardResponseDto responseDto = new RedeemGiftCardResponseDto();
        try{
            GiftCard giftCard = giftCardService.redeemGiftCard(requestDto.getGiftCardId(), requestDto.getAmountToRedeem());
            responseDto.setGiftCard(giftCard);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch(Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        
        return responseDto;
    }
}
