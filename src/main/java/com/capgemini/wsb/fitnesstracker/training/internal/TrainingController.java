package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;

    /**
     * Retrieve all trainings
     * @return List of TrainingDto objects
     */
    @GetMapping("")
    public List<TrainingDto> GetAllTrainings() {
        return trainingService.getAllTrainings()
                .stream()
                .map(trainingMapper::toTrainingDto)
                .toList();
    }

    /**
     * Retrieve Trainings by user
     * @param id user primary key
     * @return List of Training Dto objects
     */
    @GetMapping("/{id}")
    public List<TrainingDto> GetTrainingsByUser(@PathVariable Long id){
        return trainingService.getAllTrainingsByUser(id)
                .stream()
                .map(trainingMapper::toTrainingDto)
                .toList();
    }

    /**
     * Retrieve list of Trainings finished after given date
     * @param afterTime date after all finished trainings need to be retrieved
     * @return List of Training Dto objects
     * @throws ParseException exception when not able to parse
     */
    @GetMapping("/finished/{afterTime}")
    public ResponseEntity<List<TrainingDto>> GetTrainingsByFinishedDate(@PathVariable String afterTime) throws ParseException {
        SimpleDateFormat dateBase = new SimpleDateFormat("yyyy-MM-dd");
        Date dateLocal = dateBase.parse(afterTime);
        /*
        return trainingService.getAllTrainingsByFinishedDate(dateBase)
                .stream()
                .map(trainingMapper::toTrainingDto)
                .toList();

         */
        List<TrainingDto> trainingsDto = trainingService
                .getAllTrainingsByFinishedDate(dateLocal)
                .stream()
                .map(trainingMapper::toTrainingDto)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(trainingsDto);
    }

}
