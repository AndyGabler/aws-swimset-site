package io.github.andygabler.swimsetplannerbackend.calendar;

import io.github.andygabler.swimsetplannerbackend.model.SwimSet;
import io.github.andygabler.swimsetplannerbackend.model.Workout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class WorkoutLookupServiceTest {

    private WorkoutLookupService objectUnderTest;
    private WorkoutRepository workoutRepository;

    private final LocalDate august1st = LocalDate.of(2025, 8, 1);
    private final LocalDate august2nd = LocalDate.of(2025, 8, 2);
    private final LocalDate august3rd = LocalDate.of(2025, 8, 3);


    @BeforeEach
    public void setup() {
        workoutRepository = Mockito.mock(WorkoutRepository.class);

        final SwimSet ten50sFree = new SwimSet();
        ten50sFree.setId(1L);
        ten50sFree.setName("10x50s Free");
        ten50sFree.setRepCount(10);
        ten50sFree.setRepLength(50);
        ten50sFree.setDescription("Ten fifty yard freestyles");

        final SwimSet five100sFree = new SwimSet();
        five100sFree.setId(2L);
        five100sFree.setName("5x100s Free");
        five100sFree.setRepCount(5);
        five100sFree.setRepLength(100);
        five100sFree.setDescription("Five one-hundred yard freestyles");

        final SwimSet ten100sFly = new SwimSet();
        ten100sFly.setId(3L);
        ten100sFly.setName("10x100s Fly");
        ten100sFly.setRepCount(10);
        ten100sFly.setRepLength(100);
        ten100sFly.setDescription("Ten one-hundred yard butterflies");

        final SwimSet skipsDrill = new SwimSet();
        skipsDrill.setId(4L);
        skipsDrill.setName("S.K.I.P.S Drill");
        skipsDrill.setRepCount(5);
        skipsDrill.setRepLength(100);
        skipsDrill.setDescription("Continuous 5x100 yards in the pattern Swim, Kick, IM, Pull, Swim");

        final SwimSet six150sPull = new SwimSet();
        six150sPull.setId(5L);
        six150sPull.setName("6x150s Pull");
        six150sPull.setRepCount(6);
        six150sPull.setRepLength(150);
        six150sPull.setDescription("Six one-hundred fifty yard freestyles");

        final SwimSet two3Breath50s = new SwimSet();
        two3Breath50s.setId(6L);
        two3Breath50s.setName("2x50s Free 3-Breath");
        two3Breath50s.setRepCount(2);
        two3Breath50s.setRepLength(50);
        two3Breath50s.setDescription("Two fifty freestyles with only 3 breaths each");

        final Workout workout1 = new Workout();
        workout1.setId(1L);
        workout1.setOrder(1);
        workout1.setDateScheduled(august1st);
        workout1.setSwimSet(ten50sFree);

        final Workout workout2 = new Workout();
        workout2.setId(2L);
        workout2.setOrder(2);
        workout2.setDateScheduled(august1st);
        workout2.setSwimSet(ten100sFly);

        final Workout workout3 = new Workout();
        workout3.setId(3L);
        workout3.setOrder(3);
        workout3.setDateScheduled(august1st);
        workout3.setSwimSet(two3Breath50s);

        final Workout workout4 = new Workout();
        workout4.setId(4L);
        workout4.setOrder(1);
        workout4.setDateScheduled(august2nd);
        workout4.setSwimSet(five100sFree);

        final Workout workout5 = new Workout();
        workout5.setId(5L);
        workout5.setOrder(2);
        workout5.setDateScheduled(august2nd);
        workout5.setSwimSet(six150sPull);

        final Workout workout6 = new Workout();
        workout6.setId(6L);
        workout6.setOrder(3);
        workout6.setDateScheduled(august2nd);
        workout6.setSwimSet(two3Breath50s);

        final Workout workout7 = new Workout();
        workout7.setId(7L);
        workout7.setOrder(1);
        workout7.setDateScheduled(august3rd);
        workout7.setSwimSet(ten50sFree);

        final Workout workout8 = new Workout();
        workout8.setId(8L);
        workout8.setOrder(2);
        workout8.setDateScheduled(august3rd);
        workout8.setSwimSet(skipsDrill);

        Mockito.when(workoutRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(workoutRepository.findById(1L)).thenReturn(Optional.of(workout1));
        Mockito.when(workoutRepository.findById(2L)).thenReturn(Optional.of(workout2));
        Mockito.when(workoutRepository.findById(3L)).thenReturn(Optional.of(workout3));
        Mockito.when(workoutRepository.findById(4L)).thenReturn(Optional.of(workout4));
        Mockito.when(workoutRepository.findById(5L)).thenReturn(Optional.of(workout5));
        Mockito.when(workoutRepository.findById(6L)).thenReturn(Optional.of(workout6));
        Mockito.when(workoutRepository.findById(7L)).thenReturn(Optional.of(workout7));
        Mockito.when(workoutRepository.findById(8L)).thenReturn(Optional.of(workout8));

        Mockito.when(workoutRepository.findAllByDateScheduled(august1st)).thenReturn(Arrays.asList(workout1, workout2, workout3));
        Mockito.when(workoutRepository.findAllByDateScheduled(august2nd)).thenReturn(Arrays.asList(workout4, workout5, workout6));
        Mockito.when(workoutRepository.findAllByDateScheduled(august3rd)).thenReturn(Arrays.asList(workout7, workout8));

        Mockito.when(workoutRepository.findAll()).thenReturn(
            Arrays.asList(workout1, workout2, workout3, workout4, workout5, workout6, workout7, workout8)
        );

        objectUnderTest = new WorkoutLookupService(workoutRepository);
    }

    @Test
    public void testFindall() {
        final List<Workout> result = objectUnderTest.performWorkoutLookup(null, null);

        Mockito.verify(workoutRepository, Mockito.times(1)).findAll();
        Mockito.verify(workoutRepository, Mockito.times(0)).findAllByDateScheduled(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(0)).findById(Mockito.any());

        Assertions.assertEquals(8, result.size());

        Assertions.assertEquals(1, result.get(0).getOrder());
        Assertions.assertEquals(august1st, result.get(0).getDateScheduled());
        Assertions.assertEquals(1L, result.get(0).getId());
        Assertions.assertEquals(1L, result.get(0).getSwimSet().getId());
        Assertions.assertEquals(50, result.get(0).getSwimSet().getRepLength());
        Assertions.assertEquals(10, result.get(0).getSwimSet().getRepCount());
        Assertions.assertEquals("10x50s Free", result.get(0).getSwimSet().getName());
        Assertions.assertEquals("Ten fifty yard freestyles", result.get(0).getSwimSet().getDescription());

        Assertions.assertEquals(2, result.get(1).getOrder());
        Assertions.assertEquals(august1st, result.get(1).getDateScheduled());
        Assertions.assertEquals(2L, result.get(1).getId());
        Assertions.assertEquals(3L, result.get(1).getSwimSet().getId());
        Assertions.assertEquals(100, result.get(1).getSwimSet().getRepLength());
        Assertions.assertEquals(10, result.get(1).getSwimSet().getRepCount());
        Assertions.assertEquals("10x100s Fly", result.get(1).getSwimSet().getName());
        Assertions.assertEquals("Ten one-hundred yard butterflies", result.get(1).getSwimSet().getDescription());

        Assertions.assertEquals(3, result.get(2).getOrder());
        Assertions.assertEquals(august1st, result.get(2).getDateScheduled());
        Assertions.assertEquals(3L, result.get(2).getId());
        Assertions.assertEquals(6L, result.get(2).getSwimSet().getId());
        Assertions.assertEquals(50, result.get(2).getSwimSet().getRepLength());
        Assertions.assertEquals(2, result.get(2).getSwimSet().getRepCount());
        Assertions.assertEquals("2x50s Free 3-Breath", result.get(2).getSwimSet().getName());
        Assertions.assertEquals("Two fifty freestyles with only 3 breaths each", result.get(2).getSwimSet().getDescription());

        Assertions.assertEquals(1, result.get(3).getOrder());
        Assertions.assertEquals(august2nd, result.get(3).getDateScheduled());
        Assertions.assertEquals(4L, result.get(3).getId());
        Assertions.assertEquals(2L, result.get(3).getSwimSet().getId());
        Assertions.assertEquals(100, result.get(3).getSwimSet().getRepLength());
        Assertions.assertEquals(5, result.get(3).getSwimSet().getRepCount());
        Assertions.assertEquals("5x100s Free", result.get(3).getSwimSet().getName());
        Assertions.assertEquals("Five one-hundred yard freestyles", result.get(3).getSwimSet().getDescription());

        Assertions.assertEquals(2, result.get(4).getOrder());
        Assertions.assertEquals(august2nd, result.get(4).getDateScheduled());
        Assertions.assertEquals(5L, result.get(4).getId());
        Assertions.assertEquals(5L, result.get(4).getSwimSet().getId());
        Assertions.assertEquals(150, result.get(4).getSwimSet().getRepLength());
        Assertions.assertEquals(6, result.get(4).getSwimSet().getRepCount());
        Assertions.assertEquals("6x150s Pull", result.get(4).getSwimSet().getName());
        Assertions.assertEquals("Six one-hundred fifty yard freestyles", result.get(4).getSwimSet().getDescription());

        Assertions.assertEquals(3, result.get(5).getOrder());
        Assertions.assertEquals(august2nd, result.get(5).getDateScheduled());
        Assertions.assertEquals(6L, result.get(5).getId());
        Assertions.assertEquals(6L, result.get(5).getSwimSet().getId());
        Assertions.assertEquals(50, result.get(5).getSwimSet().getRepLength());
        Assertions.assertEquals(2, result.get(5).getSwimSet().getRepCount());
        Assertions.assertEquals("2x50s Free 3-Breath", result.get(5).getSwimSet().getName());
        Assertions.assertEquals("Two fifty freestyles with only 3 breaths each", result.get(5).getSwimSet().getDescription());

        Assertions.assertEquals(1, result.get(6).getOrder());
        Assertions.assertEquals(august3rd, result.get(6).getDateScheduled());
        Assertions.assertEquals(7L, result.get(6).getId());
        Assertions.assertEquals(1L, result.get(6).getSwimSet().getId());
        Assertions.assertEquals(50, result.get(6).getSwimSet().getRepLength());
        Assertions.assertEquals(10, result.get(6).getSwimSet().getRepCount());
        Assertions.assertEquals("10x50s Free", result.get(6).getSwimSet().getName());
        Assertions.assertEquals("Ten fifty yard freestyles", result.get(6).getSwimSet().getDescription());

        Assertions.assertEquals(2, result.get(7).getOrder());
        Assertions.assertEquals(august3rd, result.get(7).getDateScheduled());
        Assertions.assertEquals(8L, result.get(7).getId());
        Assertions.assertEquals(4L, result.get(7).getSwimSet().getId());
        Assertions.assertEquals(100, result.get(7).getSwimSet().getRepLength());
        Assertions.assertEquals(5, result.get(7).getSwimSet().getRepCount());
        Assertions.assertEquals("S.K.I.P.S Drill", result.get(7).getSwimSet().getName());
        Assertions.assertEquals("Continuous 5x100 yards in the pattern Swim, Kick, IM, Pull, Swim", result.get(7).getSwimSet().getDescription());
    }

    @Test
    public void testLookupById() {
        final List<Workout> result = objectUnderTest.performWorkoutLookup(5L, null);

        Mockito.verify(workoutRepository, Mockito.times(0)).findAll();
        Mockito.verify(workoutRepository, Mockito.times(0)).findAllByDateScheduled(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(1)).findById(5L);

        Assertions.assertEquals(1, result.size());

        Assertions.assertEquals(2, result.get(0).getOrder());
        Assertions.assertEquals(august2nd, result.get(0).getDateScheduled());
        Assertions.assertEquals(5L, result.get(0).getId());
        Assertions.assertEquals(5L, result.get(0).getSwimSet().getId());
        Assertions.assertEquals(150, result.get(0).getSwimSet().getRepLength());
        Assertions.assertEquals(6, result.get(0).getSwimSet().getRepCount());
        Assertions.assertEquals("6x150s Pull", result.get(0).getSwimSet().getName());
        Assertions.assertEquals("Six one-hundred fifty yard freestyles", result.get(0).getSwimSet().getDescription());
    }

    @Test
    public void testLookupByScheduledDate() {
        final List<Workout> result = objectUnderTest.performWorkoutLookup(null, august2nd);

        Mockito.verify(workoutRepository, Mockito.times(0)).findAll();
        Mockito.verify(workoutRepository, Mockito.times(1)).findAllByDateScheduled(august2nd);
        Mockito.verify(workoutRepository, Mockito.times(0)).findById(Mockito.any());

        Assertions.assertEquals(3, result.size());

        Assertions.assertEquals(1, result.get(0).getOrder());
        Assertions.assertEquals(august2nd, result.get(0).getDateScheduled());
        Assertions.assertEquals(4L, result.get(0).getId());
        Assertions.assertEquals(2L, result.get(0).getSwimSet().getId());
        Assertions.assertEquals(100, result.get(0).getSwimSet().getRepLength());
        Assertions.assertEquals(5, result.get(0).getSwimSet().getRepCount());
        Assertions.assertEquals("5x100s Free", result.get(0).getSwimSet().getName());
        Assertions.assertEquals("Five one-hundred yard freestyles", result.get(0).getSwimSet().getDescription());

        Assertions.assertEquals(2, result.get(1).getOrder());
        Assertions.assertEquals(august2nd, result.get(1).getDateScheduled());
        Assertions.assertEquals(5L, result.get(1).getId());
        Assertions.assertEquals(5L, result.get(1).getSwimSet().getId());
        Assertions.assertEquals(150, result.get(1).getSwimSet().getRepLength());
        Assertions.assertEquals(6, result.get(1).getSwimSet().getRepCount());
        Assertions.assertEquals("6x150s Pull", result.get(1).getSwimSet().getName());
        Assertions.assertEquals("Six one-hundred fifty yard freestyles", result.get(1).getSwimSet().getDescription());

        Assertions.assertEquals(3, result.get(2).getOrder());
        Assertions.assertEquals(august2nd, result.get(2).getDateScheduled());
        Assertions.assertEquals(6L, result.get(2).getId());
        Assertions.assertEquals(6L, result.get(2).getSwimSet().getId());
        Assertions.assertEquals(50, result.get(2).getSwimSet().getRepLength());
        Assertions.assertEquals(2, result.get(2).getSwimSet().getRepCount());
        Assertions.assertEquals("2x50s Free 3-Breath", result.get(2).getSwimSet().getName());
        Assertions.assertEquals("Two fifty freestyles with only 3 breaths each", result.get(2).getSwimSet().getDescription());
    }

    @Test
    public void testLookupByIdAndScheduledDate() {
        // Just verifies that when looking up by both, we just look up by ID
        final List<Workout> result = objectUnderTest.performWorkoutLookup(5L, august1st);

        Mockito.verify(workoutRepository, Mockito.times(0)).findAll();
        Mockito.verify(workoutRepository, Mockito.times(0)).findAllByDateScheduled(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(1)).findById(5L);

        Assertions.assertEquals(1, result.size());

        Assertions.assertEquals(2, result.get(0).getOrder());
        Assertions.assertEquals(august2nd, result.get(0).getDateScheduled());
        Assertions.assertEquals(5L, result.get(0).getId());
        Assertions.assertEquals(5L, result.get(0).getSwimSet().getId());
        Assertions.assertEquals(150, result.get(0).getSwimSet().getRepLength());
        Assertions.assertEquals(6, result.get(0).getSwimSet().getRepCount());
        Assertions.assertEquals("6x150s Pull", result.get(0).getSwimSet().getName());
        Assertions.assertEquals("Six one-hundred fifty yard freestyles", result.get(0).getSwimSet().getDescription());
    }

    @Test
    public void testLookupByIdNonePresent() {
        // Just verifies that when looking up by both, we just look up by ID
        Assertions.assertThrows(
            WorkoutNotFoundException.class,
            () -> objectUnderTest.performWorkoutLookup(40L, null),
            "No Workout with ID \"40\""
        );

        Mockito.verify(workoutRepository, Mockito.times(0)).findAll();
        Mockito.verify(workoutRepository, Mockito.times(0)).findAllByDateScheduled(Mockito.any());
        Mockito.verify(workoutRepository, Mockito.times(1)).findById(40L);
    }
}
