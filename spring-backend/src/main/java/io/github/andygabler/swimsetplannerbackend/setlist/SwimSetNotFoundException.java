package io.github.andygabler.swimsetplannerbackend.setlist;

public class SwimSetNotFoundException extends RuntimeException {

    public SwimSetNotFoundException(Long id) {
        super("No SwimSet with ID \"" + id + "\".");
    }
}
