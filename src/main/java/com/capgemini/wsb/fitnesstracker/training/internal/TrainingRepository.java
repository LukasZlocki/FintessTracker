package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

interface TrainingRepository extends JpaRepository<Training, Long> {

    public List<Training> findByUserId(Long id);

    @Query("SELECT t FROM Training t WHERE t.endTime > :finishDate")
    public List<Training> findTrainingsFinishedAfterGivenDate(@Param("finishDate") Date date);

}
