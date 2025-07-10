package io.github.andygabler.swimsetplannerbackend.calendar;

import io.github.andygabler.swimsetplannerbackend.model.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
public class WorkoutCalendarController {

    @Autowired
    private WorkoutRepository workoutRepository;

    @GetMapping("/setschedule")
    public List<Workout> getWorkouts(
        @RequestParam(required = false)
        LocalDate dateScheduled
    ) {
        List<Workout> results;

        if (dateScheduled != null) {
            results = workoutRepository.findAllByDateScheduled(dateScheduled);
        } else {
            results = workoutRepository.findAll();
        }

        return results;
    }

    @GetMapping("/setschedule/{id}")
    @CrossOrigin
    public List<Workout> getWorkoutsById(
        @PathVariable
        Long id
    ) {
        return workoutRepository.findAllById(Collections.singletonList(id));
    }
}
