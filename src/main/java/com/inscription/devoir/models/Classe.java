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
@Table(name = "classe")
@NoArgsConstructor
@AllArgsConstructor
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String libelle;

    private int fraisInscription;

    private int mensualite;

    private int autreFrais;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "filière", referencedColumnName = "id")
    private Filière filière;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "niveau" ,referencedColumnName = "id")
    private Niveau niveau;

    @JsonIgnore
    @OneToMany(mappedBy = "classe",cascade = CascadeType.ALL)
    private List<Inscription> inscriptions;


}
