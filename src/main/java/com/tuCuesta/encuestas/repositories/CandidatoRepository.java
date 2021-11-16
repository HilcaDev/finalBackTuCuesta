package com.tuCuesta.encuestas.repositories;

import com.tuCuesta.encuestas.models.CandidatoModel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatoRepository extends MongoRepository<CandidatoModel,String> {
    
}
