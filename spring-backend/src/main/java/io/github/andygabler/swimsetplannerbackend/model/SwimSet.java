package io.github.andygabler.swimsetplannerbackend.model;

import lombok.Data;

@Data

public class SwimSet {
    private long id;
    private int repLength;
    private int repCount;
    private String name;
    private String description;
    private String[] labels;
}
