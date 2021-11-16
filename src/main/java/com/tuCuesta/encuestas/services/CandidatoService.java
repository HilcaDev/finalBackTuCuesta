package com.tuCuesta.encuestas.services;

import java.util.List;

import com.tuCuesta.encuestas.models.CandidatoModel;
import com.tuCuesta.encuestas.repositories.CandidatoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidatoService {
    
    @Autowired
    CandidatoRepository candidatoRepository;

    public void guardarCandidato(CandidatoModel candidato){
        this.candidatoRepository.save(candidato);
    }

    public List<CandidatoModel> obtenerCandidatos(){
        return this.candidatoRepository.findAll();
    }
}
