package com.tuCuesta.encuestas.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.tuCuesta.encuestas.models.CandidatoModel;
import com.tuCuesta.encuestas.services.CandidatoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class CandidatoController {
    @Autowired
    CandidatoService candidatoService;

    @PostMapping("/candidatos")
    public ResponseEntity<Map<String,String>> guardar(@Valid @RequestBody CandidatoModel candidato){
        this.candidatoService.guardarCandidato(candidato);
        Map<String,String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "El candidato se agrego correctamente");
        respuesta.put("estado", "true");
        return ResponseEntity.ok(respuesta); 
    }

    @GetMapping("/candidatos")
    public List<CandidatoModel> mostrarCandidatos(){
       return this.candidatoService.obtenerCandidatos();
    }

}
