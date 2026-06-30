package com.example.scaler.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scaler.exceptions.InvalidLearnerException;
import com.example.scaler.models.BatchLearner;
import com.example.scaler.models.Learner;
import com.example.scaler.models.ScheduledLecture;
import com.example.scaler.repositories.BatchLearnerRepository;
import com.example.scaler.repositories.LearnerRepository;
import com.example.scaler.repositories.ScheduledLectureRepository;

@Service
public class LearnerServiceImpl implements LearnerService {

    @Autowired
    private LearnerRepository learnerRepository;

    @Autowired
    private BatchLearnerRepository batchLearnerRepository;

    @Autowired
    private ScheduledLectureRepository scheduledLectureRepository;

    @Override
    public List<ScheduledLecture> fetchTimeline(long learnerId)
            throws InvalidLearnerException {

        Learner learner = learnerRepository.findById(learnerId)
        .orElseThrow(() -> new InvalidLearnerException("Learner not found"));

        List<BatchLearner> memberships =
                batchLearnerRepository.findByLearner(learner);

        List<ScheduledLecture> result = new ArrayList<>();

        for (BatchLearner membership : memberships) {

            List<ScheduledLecture> lectures;

            if (membership.getExitDate() == null) {

                lectures =
                        scheduledLectureRepository
                                .findByBatchAndLectureStartTimeGreaterThanEqual(
                                        membership.getBatch(),
                                        membership.getEntryDate());

            } else {

                lectures =
                        scheduledLectureRepository
                                .findByBatchAndLectureStartTimeBetween(
                                        membership.getBatch(),
                                        membership.getEntryDate(),
                                        membership.getExitDate());
            }

            result.addAll(lectures);
        }

        result.sort(
                Comparator.comparing(
                        ScheduledLecture::getLectureStartTime));

        return result;
    }
}