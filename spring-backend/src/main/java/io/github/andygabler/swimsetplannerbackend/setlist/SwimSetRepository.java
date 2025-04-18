package io.github.andygabler.swimsetplannerbackend.setlist;

import io.github.andygabler.swimsetplannerbackend.model.SwimSet;
import org.springframework.data.repository.CrudRepository;

public interface SwimSetRepository extends CrudRepository<SwimSet, Long>  {
}
