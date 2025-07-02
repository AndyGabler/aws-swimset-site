package io.github.andygabler.swimsetplannerbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data

@Entity
public class SwimSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "rep_length")
    private int repLength;

    @Column(name = "rep_count")
    private int repCount;

    @Column
    private String name;

    @Column
    private String description;
}
