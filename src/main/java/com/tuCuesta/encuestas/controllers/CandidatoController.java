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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.POST}) // Para que se pueda conectar con el frontend y acepte cualquier cliente
@RequestMapping("/api")

public class CandidatoController {
    @Autowired
    CandidatoService candidatoService;

    @PostMapping("/candidatos")
    public ResponseEntity<Map<String,String>> guardar(@Valid @RequestBody CandidatoModel candidato, Errors error){
        if(error.hasErrors()){ // Hay un error, evita que se guarden datos nulos en la BD
            throwError(error);

        }
        
        this.candidatoService.guardarCandidato(candidato);
        Map<String,String> respuesta = new HashMap<>();
        respuesta.put("mensaje", "El candidato se agregó correctamente");
        respuesta.put("estado", "true");
        return ResponseEntity.ok(respuesta); 
    }

    @GetMapping("/candidatos")
    public List<CandidatoModel> mostrar(){
       return this.candidatoService.obtenerCandidatos();
    }

    // Metodo para el manejo de errores
    public void throwError(Errors error){
        String mensaje="";
        int index=0;

        for(ObjectError e: error.getAllErrors()){
            if(index>0){
                mensaje +=" | ";
            }
            mensaje+=String.format("Parametro: %s - Mensaje: %s", e.getObjectName(),e.getDefaultMessage());
        }
        throw new CustomException(mensaje);

    }

}
