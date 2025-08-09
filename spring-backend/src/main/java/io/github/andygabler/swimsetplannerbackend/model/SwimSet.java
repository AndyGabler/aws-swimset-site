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
    private Long id;

    @Column(name = "rep_length")
    private Integer repLength;

    @Column(name = "rep_count")
    private Integer repCount;

    @Column
    private String name;

    @Column
    private String description;
}
