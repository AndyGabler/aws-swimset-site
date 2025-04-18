package io.github.andygabler.swimsetplannerbackend.model;

import lombok.Data;

@Data
public class ScheduledSet {
    private long id;
    private String dateScheduled;
    private int order;
    private SwimSet scheduledSet;
}
