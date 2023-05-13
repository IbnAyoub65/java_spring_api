package com.inscription.devoir.repositories;

import com.inscription.devoir.models.AnneeScolaire;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface AnneeScolaireRepositorie extends JpaRepository<AnneeScolaire, UUID> {
}
