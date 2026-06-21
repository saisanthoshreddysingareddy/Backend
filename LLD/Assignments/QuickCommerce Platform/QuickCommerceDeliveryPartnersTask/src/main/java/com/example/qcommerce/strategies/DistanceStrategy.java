package com.example.qcommerce.strategies;

import com.example.qcommerce.models.Location;
import com.example.qcommerce.models.Partner;
import com.example.qcommerce.models.PartnerTaskMapping;
import com.example.qcommerce.models.Task;
import com.example.qcommerce.utils.DistanceUtils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class DistanceStrategy implements PartnerMatchingStrategy{
    public List<PartnerTaskMapping> match(List<Partner> partnersList, List<Task> tasksList){
        // Find location between task and partner
        List<PartnerTaskMapping> partnerTaskMappingsList = new ArrayList<>();
        List<Long> matchedPartnerIds = new ArrayList<>();

        for(Task task : tasksList){
            Partner bestPartnerMatch = null;
            double nearestLocation = Double.MAX_VALUE;
            Location taskLocation = task.getPickupLocation();
            for(Partner partner : partnersList){
                if(matchedPartnerIds.contains(partner.getId()) ){
                    continue;
                }

                Location partnerLocation = partner.getCurrentLocation();
                // Calculate distance
                double calculatedDistance = DistanceUtils.calculateDistance(taskLocation, partnerLocation);
                if(calculatedDistance < nearestLocation){
                    nearestLocation = calculatedDistance;
                    bestPartnerMatch = partner;
                }
            }
            if(bestPartnerMatch != null){
                PartnerTaskMapping taskMapping = new PartnerTaskMapping();
                taskMapping.setPartner(bestPartnerMatch);
                taskMapping.setTask(task);
                partnerTaskMappingsList.add(taskMapping);
                matchedPartnerIds.add(bestPartnerMatch.getId());
            }
        }
        return partnerTaskMappingsList;
    }

}
