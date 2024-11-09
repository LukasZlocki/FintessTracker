package com.capgemini.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingServiceImpl trainingService;

    @GetMapping("")
    public List<TrainingDto> GetTrainings() {
        return trainingService.findAllTrainings()
                .stream()
                .map(trainingMapper::toTrainingDto)
                .toList();
    }

}
