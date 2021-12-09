package com.tuCuesta.encuestas.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.tuCuesta.encuestas.exceptions.CustomException;
import com.tuCuesta.encuestas.models.CandidatoModel;
import com.tuCuesta.encuestas.services.CandidatoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*") 
@RequestMapping("/api")

public class CandidatoController {
    @Autowired
    CandidatoService candidatoService;

    @PostMapping("/candidatos")
    public ResponseEntity<Map<String,String>> guardarCandidato(@Valid @RequestBody CandidatoModel candidato){  
        this.candidatoService.guardarCandidatos(candidato);
        Map<String,String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "El candidato se agregó correctamente");
        respuesta.put("estado", "true");
        return ResponseEntity.ok(respuesta); 
    }

    @GetMapping("/candidatos")
    public List<CandidatoModel> traerCandidatos(){
       return this.candidatoService.traerCandidatos();
    }

    @GetMapping("/candidatos/{id}")
    public CandidatoModel traerEquipoPorId(@PathVariable String id){
       return this.candidatoService.buscarPorId(id).get();
    }

   

}
