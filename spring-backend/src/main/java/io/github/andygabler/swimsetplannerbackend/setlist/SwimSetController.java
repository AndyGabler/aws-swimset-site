package io.github.andygabler.swimsetplannerbackend.setlist;

import io.github.andygabler.swimsetplannerbackend.model.SwimSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
public class SwimSetController {

    @Autowired
    private SwimSetRepository swimSetRepository;

    @GetMapping("/swimsets")
    @CrossOrigin
    public List<SwimSet> getSwimSets() {
        return StreamSupport.stream(swimSetRepository.findAll().spliterator(), false).toList();
    }
}
