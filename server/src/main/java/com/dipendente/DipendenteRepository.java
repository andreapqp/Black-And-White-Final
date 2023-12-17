package com.dipendente;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DipendenteRepository extends MongoRepository<Dipendente, String> {

    Optional<Dipendente> findByUsername(String username);}
