package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TrainingDto {
    private Long id;
    private User user;
    private Date startTime;
    private Date endTime;
    private ActivityType activityType;
    private double distance;
    private double averageSpeed;

    public TrainingDto(Long id,
                       User user,
                       Date startTime,
                       Date endTime,
                       ActivityType activityType,
                       double distance,
                       double averageSpeed) {
        this.id = id;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }

    public TrainingDto updateTraining(TrainingDto trainingDto) {
        return new TrainingDto(
                trainingDto.id == null ? id : trainingDto.id,
                trainingDto.getUser() == null ? user : trainingDto.getUser(),
                trainingDto.getStartTime() == null ? startTime : trainingDto.getStartTime(),
                trainingDto.getEndTime() == null ? endTime : trainingDto.getEndTime(),
                trainingDto.getActivityType() == null ? activityType : trainingDto.getActivityType(),
                trainingDto.getDistance() == 0 ? distance : trainingDto.getDistance(),
                trainingDto.getAverageSpeed() == 0 ? averageSpeed : trainingDto.getAverageSpeed()
        );
    }

}
