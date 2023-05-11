package com.inscription.devoir.repositories;

import com.inscription.devoir.models.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaiementRepositorie extends JpaRepository<Paiement, UUID > {
}
