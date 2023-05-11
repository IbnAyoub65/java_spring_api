package com.inscription.devoir.repositories;

import com.inscription.devoir.models.AnneeScolaire;
import com.inscription.devoir.models.Classe;
import com.inscription.devoir.models.Etudiant;
import com.inscription.devoir.models.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InscriptionRepositorie extends JpaRepository< Inscription,UUID> {

    Optional<Inscription> findByEtudiantAndClasseAndAnneeScolaire(Etudiant etudiant, Classe classe, AnneeScolaire anneeScolaire);
    public Inscription findByClasse(Classe classe);
    public Inscription findByAnneeScolaire(AnneeScolaire anneeScolaire);
    List<Inscription> findByClasseAndAndAnneeScolaire(Classe classe, AnneeScolaire anneeScolaire);
    public Inscription findByEtudiant(Etudiant etudiant);
    //public Inscription findByEtudiant(Etudiant etudiant);

}
