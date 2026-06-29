package com.example.scaler.services;

import com.example.scaler.exceptions.InvalidBatchException;
import com.example.scaler.exceptions.InvalidInstructorException;
import com.example.scaler.exceptions.InvalidLectureException;
import com.example.scaler.models.*;
import com.example.scaler.repositories.BatchRepository;
import com.example.scaler.repositories.InstructorRepository;
import com.example.scaler.repositories.LectureRepository;
import com.example.scaler.repositories.ScheduledLectureRepository;
import com.example.scaler.utils.DronaUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Calendar;


@Service
@AllArgsConstructor
public class LectureServiceImpl implements LectureService{
    BatchRepository batchRepository;
    InstructorRepository instructorRepository;
    LectureRepository lectureRepository;
    ScheduledLectureRepository scheduledLectureRepository;

    // Interface method
    public List<ScheduledLecture> scheduleLectures(List<Long> lectureIds, Long instructorId, Long batchId) throws InvalidLectureException, InvalidInstructorException, InvalidBatchException{
        // Check given batch exists or not
        Optional<Batch> optionalBatch = batchRepository.findById(batchId);
        if(optionalBatch.isEmpty()){
            throw new InvalidBatchException("Batch not found");
        }
        Batch batch = optionalBatch.get();

        // Check Instructor exists or not
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if(optionalInstructor.isEmpty()){
            throw new InvalidInstructorException("Instructor not found");
        }
        Instructor instructor = optionalInstructor.get();

//        // Check given lectures exists or not
//        for(Long lectureId : lectureIds){
//            Optional<Lecture> optionalLecture = lectureRepository.findById(lectureId);
//            if(optionalLecture.isEmpty()){
//                throw new InvalidLectureException("Lecture not found");
//            }
//        }

        // Get last lecture
        Optional<ScheduledLecture> optionalScheduledLecture = scheduledLectureRepository.findTopByBatchIdOrderByLectureStartTimeDesc(batchId);

        Date currentDate;
        boolean hasPreviousLecture;

        if (optionalScheduledLecture.isPresent()) {
            currentDate = optionalScheduledLecture.get().getLectureStartTime();
            hasPreviousLecture = true;
        } else {
            currentDate = getFirstLectureDate(new Date(), batch.getSchedule());
            hasPreviousLecture = false;
        }

        List<ScheduledLecture> scheduledLectureList = new ArrayList<>();

        List<Lecture> lectures = new ArrayList<>();

        for (Long lectureId : lectureIds) {
            Lecture lecture = lectureRepository.findById(lectureId)
                    .orElseThrow(() -> new InvalidLectureException("Lecture not found"));

            lectures.add(lecture);
        }

        for (Lecture lecture : lectures) {

            if (hasPreviousLecture) {
                currentDate = getNextLectureDate(currentDate, batch.getSchedule());
            }

            ScheduledLecture sl = new ScheduledLecture();

            sl.setLecture(lecture);
            sl.setBatch(batch);
            sl.setInstructor(instructor);
            sl.setLectureStartTime(currentDate);

            Calendar end = Calendar.getInstance();
            end.setTime(currentDate);
            end.add(Calendar.HOUR_OF_DAY, 2);
            end.add(Calendar.MINUTE, 30);

            sl.setLectureEndTime(end.getTime());
            sl.setLectureLink(DronaUtils.generateUniqueLectureLink());

            scheduledLectureList.add(sl);

            hasPreviousLecture = true;
        }

        return scheduledLectureRepository.saveAll(scheduledLectureList);
    }

    private Date getNextLectureDate(Date currentDate, Schedule schedule) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (schedule) {

            case MWF_MORNING:
            case MWF_EVENING:

                if (day == Calendar.MONDAY || day == Calendar.WEDNESDAY) {
                    calendar.add(Calendar.DATE, 2);
                } else if (day == Calendar.FRIDAY) {
                    calendar.add(Calendar.DATE, 3);
                }
                break;

            case TTS_MORNING:
            case TTS_EVENING:

                if (day == Calendar.TUESDAY || day == Calendar.THURSDAY) {
                    calendar.add(Calendar.DATE, 2);
                } else if (day == Calendar.SATURDAY) {
                    calendar.add(Calendar.DATE, 3);
                }
                break;
        }

        return setLectureStartTime(calendar.getTime(), schedule);
    }

    private Date getFirstLectureDate(Date today, Schedule schedule) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        while (true) {

            int day = calendar.get(Calendar.DAY_OF_WEEK);

            switch (schedule) {

                case MWF_MORNING:
                case MWF_EVENING:

                    if (day == Calendar.MONDAY ||
                            day == Calendar.WEDNESDAY ||
                            day == Calendar.FRIDAY) {

                        return setLectureStartTime(calendar.getTime(), schedule);
                    }

                    break;

                case TTS_MORNING:
                case TTS_EVENING:

                    if (day == Calendar.TUESDAY ||
                            day == Calendar.THURSDAY ||
                            day == Calendar.SATURDAY) {

                        return setLectureStartTime(calendar.getTime(), schedule);
                    }

                    break;
            }

            calendar.add(Calendar.DATE, 1);
        }
    }

    private Date setLectureStartTime(Date date, Schedule schedule) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        switch (schedule) {

            case MWF_MORNING:
            case TTS_MORNING:
                calendar.set(Calendar.HOUR_OF_DAY, 7);
                calendar.set(Calendar.MINUTE, 0);
                break;

            case MWF_EVENING:
            case TTS_EVENING:
                calendar.set(Calendar.HOUR_OF_DAY, 21); // 9 PM
                calendar.set(Calendar.MINUTE, 0);
                break;
        }

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

}
