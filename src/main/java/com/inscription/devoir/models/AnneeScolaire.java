package com.inscription.devoir.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "anneeScolaire")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnneeScolaire {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Temporal(TemporalType.DATE)
    private Date debut;


    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Temporal(TemporalType.DATE)
    private Date fin;

    @JsonIgnore
    @OneToMany(mappedBy = "anneeScolaire",cascade = CascadeType.ALL)
    private List<Inscription> inscriptions;

}
