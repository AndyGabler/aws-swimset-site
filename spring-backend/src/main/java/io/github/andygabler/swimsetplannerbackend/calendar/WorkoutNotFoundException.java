package io.github.andygabler.swimsetplannerbackend.calendar;

public class WorkoutNotFoundException extends RuntimeException{

    public WorkoutNotFoundException(Long id) {
        super("No Workout with ID \"" + id + "\".");
    }
}
