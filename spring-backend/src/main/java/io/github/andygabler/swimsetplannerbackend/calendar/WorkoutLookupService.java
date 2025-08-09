package io.github.andygabler.swimsetplannerbackend.calendar;

import io.github.andygabler.swimsetplannerbackend.model.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class WorkoutLookupService {

    @Autowired
    private WorkoutRepository workoutRepository;

    public List<Workout> performWorkoutLookup(Long id, LocalDate dateScheduled) {
        List<Workout> results;

        if (id != null) {
            results = Collections.singletonList(
                workoutRepository.findById(id)
                    .orElseThrow(() -> new WorkoutNotFoundException(id))
            );
        } else if (dateScheduled != null) {
            results = workoutRepository.findAllByDateScheduled(dateScheduled);
        } else {
            results = workoutRepository.findAll();
        }

        return results;
    }
}
