package com.tuCuesta.encuestas.services;

import java.util.List;
import java.util.Optional;

import com.tuCuesta.encuestas.models.CandidatoModel;
import com.tuCuesta.encuestas.repositories.CandidatoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidatoService {
    
    @Autowired
    CandidatoRepository candidatoRepository;

    public void guardarCandidatos(CandidatoModel candidato){
        this.candidatoRepository.save(candidato);
    }

    public List<CandidatoModel> traerCandidatos(){
        return this.candidatoRepository.findAll();
    }

    public Optional<CandidatoModel> buscarPorId(String id) {
        return this.candidatoRepository.findById(id);
   }

   public Boolean verificar(String id) {
        return this.candidatoRepository.existsById(id);
    }

    public void eliminar(String id) {
        this.candidatoRepository.deleteById(id);
    }
}
