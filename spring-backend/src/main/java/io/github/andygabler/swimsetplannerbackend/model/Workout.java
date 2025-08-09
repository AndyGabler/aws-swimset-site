package io.github.andygabler.swimsetplannerbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "`order`")
    private Integer order;

    @Column(name = "date_scheduled")
    private LocalDate dateScheduled;

    @ManyToOne
    private SwimSet swimSet;
}
