package com.inscription.devoir.repositories;

import com.inscription.devoir.models.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NiveauRepositorie extends JpaRepository<Niveau, UUID> {
}
