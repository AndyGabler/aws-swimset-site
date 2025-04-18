package io.github.andygabler.swimsetplannerbackend.setlist;

import io.github.andygabler.swimsetplannerbackend.model.SwimSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public class HardCodedSwimSetRepository implements SwimSetRepository {

    @Override
    public <S extends SwimSet> S save(S entity) {
        return entity;
    }

    @Override
    public <S extends SwimSet> Iterable<S> saveAll(Iterable<S> entities) {
        return entities;
    }

    @Override
    public Optional<SwimSet> findById(Long aLong) {
        return getSwimSetList()
            .stream()
            .filter(swimSet -> swimSet.getId() == aLong)
            .findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        return findAllById(Collections.singletonList(aLong)).iterator().hasNext();
    }

    @Override
    public Iterable<SwimSet> findAll() {
        return getSwimSetList();
    }

    @Override
    public Iterable<SwimSet> findAllById(Iterable<Long> longs) {
        return getSwimSetList()
            .stream()
            .filter(swimSet -> StreamSupport.stream(longs.spliterator(), false).toList().contains(swimSet.getId()))
            .toList();
    }

    @Override
    public long count() {
        return getSwimSetList().size();
    }

    @Override
    public void deleteById(Long aLong) {}

    @Override
    public void delete(SwimSet entity) {}

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {}

    @Override
    public void deleteAll(Iterable<? extends SwimSet> entities) {}

    @Override
    public void deleteAll() {}

    private static ArrayList<SwimSet> getSwimSetList() {
        final SwimSet six150sFree = new SwimSet();
        six150sFree.setId(1);
        six150sFree.setRepLength(150);
        six150sFree.setRepCount(6);
        six150sFree.setName("6x150s free");
        six150sFree.setDescription("6 reps of 150 yards of freestyle");
        six150sFree.setLabels(new String[]{"Full Body", "Endurance"});

        final SwimSet ten25sBreastKick = new SwimSet();
        ten25sBreastKick.setId(2);
        ten25sBreastKick.setRepLength(25);
        ten25sBreastKick.setRepCount(10);
        ten25sBreastKick.setName("10x25s breast kick");
        ten25sBreastKick.setDescription("10 reps of 25 yards of breaststroke kick with board");
        ten25sBreastKick.setLabels(new String[]{"Legs"});

        final SwimSet ten25sFreeKick = new SwimSet();
        ten25sFreeKick.setId(3);
        ten25sFreeKick.setRepLength(25);
        ten25sFreeKick.setRepCount(10);
        ten25sFreeKick.setName("10x25s free kick");
        ten25sFreeKick.setDescription("10 reps of 25 yards of freestyle kick with board");
        ten25sFreeKick.setLabels(new String[]{"Legs"});

        final SwimSet five200sFreePull = new SwimSet();
        five200sFreePull.setId(4);
        five200sFreePull.setRepLength(200);
        five200sFreePull.setRepCount(5);
        five200sFreePull.setName("5x200s free pull");
        five200sFreePull.setDescription("5 reps of 200 yards of freestyle with pull bouy");
        five200sFreePull.setLabels(new String[]{"Arms", "Endurance"});

        final SwimSet ten100sFly = new SwimSet();
        ten100sFly.setId(5);
        ten100sFly.setRepLength(100);
        ten100sFly.setRepCount(10);
        ten100sFly.setName("10x100s fly");
        ten100sFly.setDescription("10 reps of 100 yards of butterfly.");
        ten100sFly.setLabels(new String[]{"Arms", "Full Body", "Endurance"});

        final ArrayList<SwimSet> sets = new ArrayList<>();
        sets.add(six150sFree);
        sets.add(ten25sBreastKick);
        sets.add(ten25sFreeKick);
        sets.add(five200sFreePull);
        sets.add(ten100sFly);
        return sets;
    }
}
