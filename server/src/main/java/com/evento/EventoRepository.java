package com.evento;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends MongoRepository<Evento, String> {

    List<Evento> findByData(Date data);

}
