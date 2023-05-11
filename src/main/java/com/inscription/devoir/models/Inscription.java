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
@Table(name = "inscription")
@NoArgsConstructor
@AllArgsConstructor
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dateInscription;

    private Double initialDeposit;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "etudiant" ,referencedColumnName = "id")
    private Etudiant  etudiant;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "anneeScolaire" ,referencedColumnName = "id")
    private AnneeScolaire anneeScolaire;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "classe" ,referencedColumnName = "id")
    private Classe  classe;


    @OneToMany(mappedBy = "inscription",cascade = CascadeType.ALL)
    private List<Paiement> paiements;


}
