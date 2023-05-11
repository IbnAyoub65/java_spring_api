package com.inscription.devoir.repositories;

import com.inscription.devoir.models.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EtudiantRepositorie extends JpaRepository<Etudiant, UUID> {
    public Etudiant findEtudiantByMatricule(String matricule);

}
