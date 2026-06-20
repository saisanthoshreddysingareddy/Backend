package com.example.ecom.controllers;

import com.example.ecom.dtos.GenerateRecommendationsRequestDto;
import com.example.ecom.dtos.GenerateRecommendationsResponseDto;
import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.models.Product;
import com.example.ecom.services.RecommendationsService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RecommendationsController {
    RecommendationsService recommendationsService;
    // Constructor
    public RecommendationsController(RecommendationsService recommendationsService){
        this.recommendationsService = recommendationsService;
    }

    public GenerateRecommendationsResponseDto generateRecommendations(GenerateRecommendationsRequestDto requestDto) {
        GenerateRecommendationsResponseDto responseDto = new GenerateRecommendationsResponseDto();
        try{
            List<Product> products = recommendationsService.getRecommendations(requestDto.getProductId());
            responseDto.setRecommendations(products);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch(Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        
        return responseDto;
    }
}
