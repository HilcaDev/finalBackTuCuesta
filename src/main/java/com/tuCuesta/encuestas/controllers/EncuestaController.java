package com.tuCuesta.encuestas.controllers;

import com.tuCuesta.encuestas.services.EncuestaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.tuCuesta.encuestas.models.EncuestaModel;
import com.tuCuesta.encuestas.services.EncuestaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api")
public class EncuestaController {

    @Autowired
    EncuestaService service;

    @PostMapping("/encuestas")
    public ResponseEntity<Map<String, String>> guardar(@Valid @RequestBody EncuestaModel encuesta){
        this.service.guardarEncuesta(encuesta); //Invocamos el metodo creado en el servicio
        Map<String, String> respuesta=new HashMap<>();//Creamos el map para la respuesta al cliente
        respuesta.put("mensaje", "La encuesta se agrego correctamente"); //Se agrega la respuesta que se quiere enviar
        respuesta.put("estado", "true");
        return ResponseEntity.ok(respuesta); 
    }

}
