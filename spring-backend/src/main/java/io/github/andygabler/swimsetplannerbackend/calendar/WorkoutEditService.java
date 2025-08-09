package io.github.andygabler.swimsetplannerbackend.calendar;

import io.github.andygabler.swimsetplannerbackend.model.Workout;
import io.github.andygabler.swimsetplannerbackend.setlist.SwimSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class WorkoutEditService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private SwimSetRepository swimSetRepository;

    public Workout editWorkout(Workout inWorkout, Long id) {
        final boolean isNewWorkout = id == null;
        /*
         Set the workout to save as the workout. If the ID of the workout we are intending to edit is present,
         then do a lookup. If that doesn't exist, throw an exception because we don't want to unintentionally create
         a new row.
         */
        Workout workoutToSave = inWorkout;
        if (!isNewWorkout) {
            workoutToSave = workoutRepository.findById(id)
                .orElseThrow(() -> new WorkoutNotFoundException(id));
        } else {
            Assert.notNull(inWorkout.getDateScheduled(), "For new Workout, dateScheduled must be set.");
            Assert.notNull(inWorkout.getSwimSet(), "For new Workout, swimSet must be set with \"id\" field.");
            Assert.notNull(inWorkout.getSwimSet().getId(), "For new Workout, swimSet must be set with \"id\" field.");
        }

        if (inWorkout.getOrder() != null) {
            workoutToSave.setOrder(inWorkout.getOrder());
        }
        if (inWorkout.getDateScheduled() != null) {
            workoutToSave.setDateScheduled(inWorkout.getDateScheduled());
        }
        if (
            inWorkout.getSwimSet() != null &&
            (
                isNewWorkout ||
                workoutToSave.getSwimSet() == null ||
                !workoutToSave.getSwimSet().getId().equals(inWorkout.getSwimSet().getId())
            )
        ) {
            final long swimSetId = inWorkout.getSwimSet().getId();
            // Get swimset from the database so that this returns a fully loaded swim set
            workoutToSave.setSwimSet(
                swimSetRepository.findById(swimSetId)
                    .orElseThrow(() -> new IllegalArgumentException("No SwimSet with ID \"" + swimSetId + "\"."))
            );
        }

        // If a new workout has no order, get latest from the database
        // TODO this feels logic-Y and not Restful
        if (
            isNewWorkout &&
            workoutToSave.getOrder() == null &&
            workoutToSave.getDateScheduled() != null
        ) {
            // TODO this does create a race condition, unique index should protect against it for now
            Integer order = workoutRepository.findMaxOrderForDateScheduled(workoutToSave.getDateScheduled());
            if (order == null) {
                order = 1;
            } else {
                order++;
            }
            workoutToSave.setOrder(order);
        }

        return workoutRepository.save(workoutToSave);
    }

    public void deleteWorkout(Long id) {
        workoutRepository.delete(
            workoutRepository.findById(id).orElseThrow(() -> new WorkoutNotFoundException(id))
        );
    }
}
