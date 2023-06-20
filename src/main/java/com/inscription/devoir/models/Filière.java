package com.inscription.devoir.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter@Setter
@Entity
@Table(name = "filière")
@NoArgsConstructor
@AllArgsConstructor
public class Filière {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String libelle;
    @JsonIgnore
    @OneToMany(mappedBy = "filière",cascade = CascadeType.ALL)
    private List<Classe> classes;


}
