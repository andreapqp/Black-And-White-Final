package com.assenza;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssenzaRepository extends MongoRepository<Assenza, String> {
    
    Optional<Assenza> findByData(Date data);
    
    List<Assenza> findByDipendentiContaining(String dipendenteId);

    List<Assenza> findByDipendentiContainingAndData(String dipendenteId, Date data);
    
}
