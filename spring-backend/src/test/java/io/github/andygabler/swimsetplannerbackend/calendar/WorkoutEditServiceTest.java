package io.github.andygabler.swimsetplannerbackend.calendar;

import io.github.andygabler.swimsetplannerbackend.model.SwimSet;
import io.github.andygabler.swimsetplannerbackend.model.Workout;
import io.github.andygabler.swimsetplannerbackend.setlist.SwimSetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

public class WorkoutEditServiceTest {

    private WorkoutEditService objectUnderTest;
    private WorkoutRepository workoutRepository;
    private SwimSetRepository swimSetRepository;

    private Workout existingWorkout;
    private SwimSet ten50sFree;
    private SwimSet five100sFree;

    private final LocalDate august1st = LocalDate.of(2025, 8, 1);
    private final LocalDate august2nd = LocalDate.of(2025, 8, 2);

    @BeforeEach
    public void setup() {
        ten50sFree = new SwimSet();
        ten50sFree.setId(1L);
        ten50sFree.setName("10x50s Free");
        ten50sFree.setDescription("Ten fifty yard freestyles");
        ten50sFree.setRepCount(10);
        ten50sFree.setRepLength(50);

        five100sFree = new SwimSet();
        five100sFree.setId(2L);
        five100sFree.setName("5x100s Free");
        five100sFree.setDescription("Five one-hundred yard freestyles");
        five100sFree.setRepCount(5);
        five100sFree.setRepLength(100);

        existingWorkout = Mockito.mock(Workout.class);
        Mockito.when(existingWorkout.getId()).thenReturn(1L);
        Mockito.when(existingWorkout.getOrder()).thenReturn(3);
        Mockito.when(existingWorkout.getDateScheduled()).thenReturn(august1st);
        Mockito.when(existingWorkout.getSwimSet()).thenReturn(ten50sFree);

        workoutRepository = Mockito.mock(WorkoutRepository.class);
        Mockito.when(workoutRepository.findMaxOrderForDateScheduled(Mockito.any())).thenReturn(null);
        Mockito.when(workoutRepository.findMaxOrderForDateScheduled(august1st)).thenReturn(3);
        Mockito.when(workoutRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(workoutRepository.findById(1L)).thenReturn(Optional.of(existingWorkout));
        Mockito.when(workoutRepository.save(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        swimSetRepository = Mockito.mock(SwimSetRepository.class);
        Mockito.when(swimSetRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(swimSetRepository.findById(1L)).thenReturn(Optional.of(ten50sFree));
        Mockito.when(swimSetRepository.findById(2L)).thenReturn(Optional.of(five100sFree));

        objectUnderTest = new WorkoutEditService(workoutRepository, swimSetRepository);
    }

    @Test
    public void testAddNewWorkoutIdSet() {
        final Workout inputWorkout = new Workout();
        inputWorkout.setId(2L);

        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> objectUnderTest.editWorkout(inputWorkout, null),
            "For new Workout, ID cannot be set."
        );

        Mockito.verify(workoutRepository, Mockito.times(0)).findById(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(0)).findMaxOrderForDateScheduled(Mockito.any());
        Mockito.verify(swimSetRepository, Mockito.times(0)).findById(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void testAddNewWorkoutDateScheduledNull() {
        final Workout inputWorkout = new Workout();

        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> objectUnderTest.editWorkout(inputWorkout, null),
            "For new Workout, dateScheduled must be set."
        );

        Mockito.verify(workoutRepository, Mockito.times(0)).findById(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(0)).findMaxOrderForDateScheduled(Mockito.any());
        Mockito.verify(swimSetRepository, Mockito.times(0)).findById(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void testAddNewWorkoutSwimSetNull() {
        final Workout inputWorkout = new Workout();
        inputWorkout.setDateScheduled(august1st);

        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> objectUnderTest.editWorkout(inputWorkout, null),
            "For new Workout, swimSet must be set with \"id\" field."
        );

        Mockito.verify(workoutRepository, Mockito.times(0)).findById(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(0)).findMaxOrderForDateScheduled(Mockito.any());
        Mockito.verify(swimSetRepository, Mockito.times(0)).findById(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void testAddNewWorkoutSwimSetIdNull() {
        final Workout inputWorkout = new Workout();
        inputWorkout.setDateScheduled(august1st);
        inputWorkout.setSwimSet(new SwimSet());

        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> objectUnderTest.editWorkout(inputWorkout, null),
            "For new Workout, swimSet must be set with \"id\" field."
        );

        Mockito.verify(workoutRepository, Mockito.times(0)).findById(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(0)).findMaxOrderForDateScheduled(Mockito.any());
        Mockito.verify(swimSetRepository, Mockito.times(0)).findById(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void testAddNewWorkoutNoOrder() {
        final Workout inputWorkout = new Workout();
        inputWorkout.setDateScheduled(august1st);
        final SwimSet swimSet = new SwimSet();
        swimSet.setId(1L);
        inputWorkout.setSwimSet(swimSet);

        final Workout outWorkout = objectUnderTest.editWorkout(inputWorkout, null);
        Assertions.assertEquals(august1st, outWorkout.getDateScheduled());
        Assertions.assertEquals(4, outWorkout.getOrder());
        Assertions.assertEquals(ten50sFree, outWorkout.getSwimSet());

        Mockito.verify(workoutRepository, Mockito.times(0)).findById(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(1)).findMaxOrderForDateScheduled(august1st);
        Mockito.verify(swimSetRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(workoutRepository, Mockito.times(1)).save(inputWorkout);
    }

    @Test
    public void testAddNewWorkoutNoOrderFirstSetOfDay() {
        final Workout inputWorkout = new Workout();
        inputWorkout.setDateScheduled(august2nd);
        final SwimSet swimSet = new SwimSet();
        swimSet.setId(2L);
        inputWorkout.setSwimSet(swimSet);

        final Workout outWorkout = objectUnderTest.editWorkout(inputWorkout, null);
        Assertions.assertEquals(august2nd, outWorkout.getDateScheduled());
        Assertions.assertEquals(1, outWorkout.getOrder());
        Assertions.assertEquals(five100sFree, outWorkout.getSwimSet());

        Mockito.verify(workoutRepository, Mockito.times(0)).findById(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(1)).findMaxOrderForDateScheduled(august2nd);
        Mockito.verify(swimSetRepository, Mockito.times(1)).findById(2L);
        Mockito.verify(workoutRepository, Mockito.times(1)).save(inputWorkout);
    }

    @Test
    public void testAddNewWorkoutBadSwimSet() {
        final Workout inputWorkout = new Workout();
        inputWorkout.setDateScheduled(august2nd);
        final SwimSet swimSet = new SwimSet();
        swimSet.setId(5L);
        inputWorkout.setSwimSet(swimSet);

        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> objectUnderTest.editWorkout(inputWorkout, null),
            "No SwimSet with ID \"5\"."
        );

        Mockito.verify(workoutRepository, Mockito.times(0)).findById(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(0)).findMaxOrderForDateScheduled(Mockito.any());
        Mockito.verify(swimSetRepository, Mockito.times(1)).findById(5L);
        Mockito.verify(workoutRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void testAddNewWorkoutHappyPath() {
        final Workout inputWorkout = new Workout();
        inputWorkout.setDateScheduled(august2nd);
        inputWorkout.setOrder(6);
        final SwimSet swimSet = new SwimSet();
        swimSet.setId(2L);
        swimSet.setName("fhadhfkjsadhfk");
        swimSet.setDescription("aucnmaoklhfuidahf");
        swimSet.setRepCount(8298);
        swimSet.setRepLength(432843);
        inputWorkout.setSwimSet(swimSet);

        final Workout outWorkout = objectUnderTest.editWorkout(inputWorkout, null);
        Assertions.assertEquals(august2nd, outWorkout.getDateScheduled());
        Assertions.assertEquals(6, outWorkout.getOrder());
        Assertions.assertEquals(five100sFree, outWorkout.getSwimSet());
        Assertions.assertEquals(2L, outWorkout.getSwimSet().getId());
        Assertions.assertEquals("5x100s Free", outWorkout.getSwimSet().getName());
        Assertions.assertEquals("Five one-hundred yard freestyles", outWorkout.getSwimSet().getDescription());
        Assertions.assertEquals(5, outWorkout.getSwimSet().getRepCount());
        Assertions.assertEquals(100, outWorkout.getSwimSet().getRepLength());

        Mockito.verify(workoutRepository, Mockito.times(0)).findById(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(0)).findMaxOrderForDateScheduled(Mockito.any());
        Mockito.verify(swimSetRepository, Mockito.times(1)).findById(2L);
        Mockito.verify(workoutRepository, Mockito.times(1)).save(inputWorkout);
    }

    @Test
    public void testEditWorkoutNotFound() {
        final Workout inputWorkout = new Workout();
        inputWorkout.setId(6L);

        final SwimSet swimSet = new SwimSet();
        swimSet.setId(2L);
        swimSet.setName("fhadhfkjsadhfk");
        swimSet.setDescription("aucnmaoklhfuidahf");
        swimSet.setRepCount(8298);
        swimSet.setRepLength(432843);
        inputWorkout.setSwimSet(swimSet);

        Assertions.assertThrows(
            WorkoutNotFoundException.class,
            () -> objectUnderTest.editWorkout(inputWorkout, inputWorkout.getId()),
            "No Workout with ID \"6\""
        );

        Mockito.verify(workoutRepository, Mockito.times(1)).findById(6L);
        Mockito.verify(workoutRepository, Mockito.times(0)).findMaxOrderForDateScheduled(Mockito.any());
        Mockito.verify(swimSetRepository, Mockito.times(0)).findById(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void testEditWorkoutDifferentSwimSet() {
        final Workout inputWorkout = new Workout();
        inputWorkout.setId(1L);

        final SwimSet swimSet = new SwimSet();
        swimSet.setId(2L);
        swimSet.setName("fhadhfkjsadhfk");
        swimSet.setDescription("aucnmaoklhfuidahf");
        swimSet.setRepCount(8298);
        swimSet.setRepLength(432843);
        inputWorkout.setSwimSet(swimSet);

        final Workout outWorkout = objectUnderTest.editWorkout(inputWorkout, inputWorkout.getId());
        Assertions.assertEquals(existingWorkout, outWorkout);

        Mockito.verify(existingWorkout, Mockito.times(1)).setSwimSet(five100sFree);
        Mockito.verify(existingWorkout, Mockito.times(0)).setOrder(Mockito.any());
        Mockito.verify(existingWorkout, Mockito.times(0)).setDateScheduled(Mockito.any());
        Mockito.verify(existingWorkout, Mockito.times(0)).setId(Mockito.any());

        Mockito.verify(workoutRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(workoutRepository, Mockito.times(0)).findMaxOrderForDateScheduled(Mockito.any());
        Mockito.verify(swimSetRepository, Mockito.times(1)).findById(2L);
        Mockito.verify(workoutRepository, Mockito.times(1)).save(existingWorkout);
    }

    @Test
    public void testEditWorkoutSameSwimSet() {
        final Workout inputWorkout = new Workout();
        inputWorkout.setId(1L);

        final SwimSet swimSet = new SwimSet();
        swimSet.setId(1L);
        inputWorkout.setSwimSet(swimSet);

        final Workout outWorkout = objectUnderTest.editWorkout(inputWorkout, inputWorkout.getId());
        Assertions.assertEquals(existingWorkout, outWorkout);

        Mockito.verify(existingWorkout, Mockito.times(0)).setSwimSet(Mockito.any());
        Mockito.verify(existingWorkout, Mockito.times(0)).setOrder(Mockito.any());
        Mockito.verify(existingWorkout, Mockito.times(0)).setDateScheduled(Mockito.any());
        Mockito.verify(existingWorkout, Mockito.times(0)).setId(Mockito.any());

        Mockito.verify(workoutRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(workoutRepository, Mockito.times(0)).findMaxOrderForDateScheduled(Mockito.any());
        Mockito.verify(swimSetRepository, Mockito.times(0)).findById(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(1)).save(existingWorkout);
    }

    @Test
    public void testEditWorkoutSetDateScheduled() {
        final Workout inputWorkout = new Workout();
        inputWorkout.setId(1L);
        inputWorkout.setDateScheduled(august2nd);

        final Workout outWorkout = objectUnderTest.editWorkout(inputWorkout, inputWorkout.getId());
        Assertions.assertEquals(existingWorkout, outWorkout);

        Mockito.verify(existingWorkout, Mockito.times(0)).setSwimSet(Mockito.any());
        Mockito.verify(existingWorkout, Mockito.times(0)).setOrder(Mockito.any());
        Mockito.verify(existingWorkout, Mockito.times(1)).setDateScheduled(august2nd);
        Mockito.verify(existingWorkout, Mockito.times(0)).setId(Mockito.any());

        Mockito.verify(workoutRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(workoutRepository, Mockito.times(0)).findMaxOrderForDateScheduled(Mockito.any());
        Mockito.verify(swimSetRepository, Mockito.times(0)).findById(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(1)).save(existingWorkout);
    }

    @Test
    public void testEditWorkoutSetOrder() {
        final Workout inputWorkout = new Workout();
        inputWorkout.setId(1L);
        inputWorkout.setOrder(1335);

        final Workout outWorkout = objectUnderTest.editWorkout(inputWorkout, inputWorkout.getId());
        Assertions.assertEquals(existingWorkout, outWorkout);

        Mockito.verify(existingWorkout, Mockito.times(0)).setSwimSet(Mockito.any());
        Mockito.verify(existingWorkout, Mockito.times(1)).setOrder(1335);
        Mockito.verify(existingWorkout, Mockito.times(0)).setDateScheduled(Mockito.any());
        Mockito.verify(existingWorkout, Mockito.times(0)).setId(Mockito.any());

        Mockito.verify(workoutRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(workoutRepository, Mockito.times(0)).findMaxOrderForDateScheduled(Mockito.any());
        Mockito.verify(swimSetRepository, Mockito.times(0)).findById(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(1)).save(existingWorkout);
    }

    @Test
    public void testDeleteWorkoutNotFound() {
        Assertions.assertThrows(
            WorkoutNotFoundException.class,
            () -> objectUnderTest.deleteWorkout(50L),
            "No Workout with ID \"50\"."
        );

        Mockito.verify(workoutRepository, Mockito.times(1)).findById(50L);
        Mockito.verify(workoutRepository, Mockito.times(0)).delete(Mockito.any());
    }

    @Test
    public void testDeleteHappyPath() {
        objectUnderTest.deleteWorkout(1L);

        Mockito.verify(workoutRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(workoutRepository, Mockito.times(1)).delete(existingWorkout);
    }
}
