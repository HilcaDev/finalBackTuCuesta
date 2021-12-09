package com.tuCuesta.encuestas.services;

import java.util.List;

import com.tuCuesta.encuestas.models.EncuestaModel;
import com.tuCuesta.encuestas.repositories.EncuestaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncuestaService {
    
    @Autowired
    EncuestaRepository encuestaRepository;

    public void guardarEncuesta(EncuestaModel encuesta){
        this.encuestaRepository.save(encuesta);

    }

    public List<EncuestaModel> traerEncuestas(){
        return this.encuestaRepository.findAll();
    }

    public EncuestaModel getEncuesta(String id){
        return  this.encuestaRepository.findById(id).orElse(new EncuestaModel());
      }
}
