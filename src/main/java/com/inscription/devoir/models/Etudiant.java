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


@Getter@Setter
@Entity
@Table(name = "etudiant")
@NoArgsConstructor
@AllArgsConstructor
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nom;
    private String prenom;

    private  String matricule;
    private String adresse;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date datenaiss;

    private String telephone;

    private String photo;

    @OneToMany(mappedBy = "etudiant")
    private List<Inscription> inscriptions;

    @Column(nullable = true)
    private Double solde;


}
