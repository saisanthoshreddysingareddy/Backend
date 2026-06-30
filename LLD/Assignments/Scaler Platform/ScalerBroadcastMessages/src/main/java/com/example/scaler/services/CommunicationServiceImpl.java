package com.example.scaler.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.scaler.adapters.EmailAdapter;
import com.example.scaler.adapters.WhatsappAdapter;
import com.example.scaler.exceptions.InvalidBatchException;
import com.example.scaler.exceptions.InvalidUserException;
import com.example.scaler.exceptions.UnAuthorizedAccessException;
import com.example.scaler.models.Batch;
import com.example.scaler.models.BatchLearner;
import com.example.scaler.models.Communication;
import com.example.scaler.models.CommunicationLearner;
import com.example.scaler.models.Learner;
import com.example.scaler.models.User;
import com.example.scaler.models.UserType;
import com.example.scaler.repositories.BatchLearnerRepository;
import com.example.scaler.repositories.BatchRepository;
import com.example.scaler.repositories.CommunicationLearnerRepository;
import com.example.scaler.repositories.CommunicationRepository;
import com.example.scaler.repositories.LearnerRepository;
import com.example.scaler.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommunicationServiceImpl implements CommunicationService {
    UserRepository userRepository;
    BatchRepository batchRepository;
    CommunicationRepository communicationRepository;
    LearnerRepository learnerRepository;
    CommunicationLearnerRepository communicationLearnerRepository;
    BatchLearnerRepository batchLearnerRepository;
    EmailAdapter emailAdapter;
    WhatsappAdapter whatsappAdapter;


    public Communication broadcastMessage(long batchId, long userId, String message) throws InvalidBatchException, InvalidUserException, UnAuthorizedAccessException{
        
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new InvalidUserException("Invalid User"));

        Batch batch = batchRepository.findById(batchId)
        .orElseThrow(() -> new InvalidBatchException("Invalid Batch"));

        if (user.getUserType() != UserType.ADMIN) {
            throw new UnAuthorizedAccessException("Only ADMIN users can broadcast messages");
        }

        Communication communication = new Communication();
        communication.setBatch(batch);
        communication.setMessage(message);
        communication.setSentBy(user);

        communication = communicationRepository.save(communication);

        List<BatchLearner> batchLearners =
                batchLearnerRepository
                        .findByBatchIdAndExitDateIsNull(batchId);

        for (BatchLearner bl : batchLearners) {

            Learner learner = bl.getLearner();

            boolean emailSent = false;
            boolean whatsappSent = false;

            try {
                emailAdapter.sendEmail(
                        learner.getEmail(),
                        message
                );
                emailSent = true;
            } catch (Exception e) {
            }

            try {
                whatsappAdapter.sendWhatsappMessage(
                        learner.getPhoneNumber(),
                        message
                );
                whatsappSent = true;
            } catch (Exception e) {
            }

            CommunicationLearner cl =
                    new CommunicationLearner();

            cl.setCommunication(communication);
            cl.setLearner(learner);
            cl.setEmailDelivered(emailSent);
            cl.setWhatsappDelivered(whatsappSent);

            communicationLearnerRepository.save(cl);
        }

        return communication;


    }

}
