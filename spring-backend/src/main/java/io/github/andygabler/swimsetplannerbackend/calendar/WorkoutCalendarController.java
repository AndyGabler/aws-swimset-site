package io.github.andygabler.swimsetplannerbackend.calendar;

import io.github.andygabler.swimsetplannerbackend.model.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class WorkoutCalendarController {

    @Autowired
    private WorkoutLookupService lookupService;

    @Autowired
    private WorkoutEditService editService;

    @GetMapping("/setschedule")
    @CrossOrigin
    public List<Workout> getWorkouts(
        @RequestParam(required = false)
        LocalDate dateScheduled
    ) {
        return lookupService.performWorkoutLookup(null, dateScheduled);
    }

    @GetMapping("/setschedule/{id}")
    @CrossOrigin
    public List<Workout> getWorkoutsById(@PathVariable Long id) {
        return lookupService.performWorkoutLookup(id, null);
    }

    @PostMapping(value = "/setschedule", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public Workout saveWorkout(@RequestBody Workout workout) {
        return editService.editWorkout(workout, workout.getId());
    }

    @PostMapping(value = "/setschedule/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public Workout saveWorkoutById(@RequestBody Workout workout, @PathVariable Long id) {
        return editService.editWorkout(workout, id);
    }

    @DeleteMapping("/setschedule/{id}")
    @CrossOrigin
    public void deleteWorkout(@PathVariable Long id) {
        editService.deleteWorkout(id);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(WorkoutNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(WorkoutNotFoundException exception) {
        return exception.getMessage();
    }
}
