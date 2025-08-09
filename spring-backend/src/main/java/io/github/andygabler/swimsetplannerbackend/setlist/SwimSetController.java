package io.github.andygabler.swimsetplannerbackend.setlist;

import io.github.andygabler.swimsetplannerbackend.model.SwimSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class SwimSetController {

    @Autowired
    private SwimSetRepository swimSetRepository;

    @GetMapping(value = {"/swimsets", "/swimsets/{id}"})
    @CrossOrigin
    public List<SwimSet> getSwimSets(
        @PathVariable(required = false)
        Long id
    ) {
        List<SwimSet> results;

        if (id == null) {
            results = swimSetRepository.findAll();
        } else {
            results = Collections.singletonList(
                swimSetRepository.findById(id)
                    .orElseThrow(() -> new SwimSetNotFoundException(id))
            );
        }

        return results;
    }

    @ExceptionHandler(SwimSetNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(SwimSetNotFoundException exception) {
        return exception.getMessage();
    }
}
