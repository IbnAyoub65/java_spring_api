package com.inscription.devoir.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter@Setter
@Entity
@Table(name = "niveau")
@NoArgsConstructor
@AllArgsConstructor
public class Niveau {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String libelle;

    @OneToMany(mappedBy = "niveau",cascade = CascadeType.ALL)
    private List<Classe> classes;


}
