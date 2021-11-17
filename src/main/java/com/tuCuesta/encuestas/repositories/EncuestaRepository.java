package com.tuCuesta.encuestas.repositories;

import com.tuCuesta.encuestas.models.EncuestaModel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EncuestaRepository extends MongoRepository<EncuestaModel,String> {
    
}
