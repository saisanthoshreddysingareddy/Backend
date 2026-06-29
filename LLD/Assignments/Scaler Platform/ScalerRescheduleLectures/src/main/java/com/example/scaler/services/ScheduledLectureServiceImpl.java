package com.example.scaler.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.scaler.exceptions.InvalidScheduledLectureException;
import com.example.scaler.models.Batch;
import com.example.scaler.models.Schedule;
import com.example.scaler.models.ScheduledLecture;
import com.example.scaler.repositories.ScheduledLectureRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduledLectureServiceImpl implements ScheduledLectureService {

    private final ScheduledLectureRepository scheduledLectureRepository;

    @Override
    public List<ScheduledLecture> rescheduleScheduledLecture(long scheduledLectureId)
            throws InvalidScheduledLectureException {

        ScheduledLecture scheduledLecture = scheduledLectureRepository
                .findById(scheduledLectureId)
                .orElseThrow(() ->
                        new InvalidScheduledLectureException("Scheduled lecture not found"));

        Batch batch = scheduledLecture.getBatch();

        List<ScheduledLecture> scheduledLectures =
                scheduledLectureRepository
                        .findAllByBatchIdOrderByLectureStartTime(batch.getId());

        int index = -1;

        for (int i = 0; i < scheduledLectures.size(); i++) {
            if (scheduledLectures.get(i).getId() == scheduledLectureId) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new InvalidScheduledLectureException("Scheduled lecture not found");
        }

        Date currentDate = scheduledLectures.get(index).getLectureStartTime();

        List<ScheduledLecture> updatedLectures = new ArrayList<>();

        for (int i = index; i < scheduledLectures.size(); i++) {

            currentDate = getNextLectureDate(currentDate, batch.getSchedule());

            ScheduledLecture lecture = scheduledLectures.get(i);

            lecture.setLectureStartTime(currentDate);

            Calendar end = Calendar.getInstance();
            end.setTime(currentDate);
            end.add(Calendar.HOUR_OF_DAY, 2);
            end.add(Calendar.MINUTE, 30);

            lecture.setLectureEndTime(end.getTime());

            updatedLectures.add(lecture);
        }

        return scheduledLectureRepository.saveAll(updatedLectures);
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
                calendar.set(Calendar.HOUR_OF_DAY, 21);
                calendar.set(Calendar.MINUTE, 0);
                break;
        }

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}
