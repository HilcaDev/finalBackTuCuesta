package com.tuCuesta.encuestas.controllers;

import com.tuCuesta.encuestas.services.EncuestaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.tuCuesta.encuestas.exceptions.CustomException;
import com.tuCuesta.encuestas.models.EncuestaModel;
import com.tuCuesta.encuestas.services.EncuestaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.POST}) // Para que se pueda conectar con el frontend y acepte cualquier cliente
@RequestMapping("/api")
public class EncuestaController {

    @Autowired
    EncuestaService service;

    @PostMapping("/encuestas")
    public ResponseEntity<Map<String, String>> guardar(@Valid @RequestBody EncuestaModel encuesta, Errors error){
        if(error.hasErrors()){ // Hay un error, evita que se guarden datos nulos en la BD
            throwError(error);

        }

        this.service.guardarEncuesta(encuesta); //Invocamos el metodo creado en el servicio
        Map<String, String> respuesta=new HashMap<>();//Creamos el map para la respuesta al cliente
        respuesta.put("mensaje", "La encuesta se agregó correctamente"); //Se agrega la respuesta que se quiere enviar
        respuesta.put("estado", "true");
        return ResponseEntity.ok(respuesta); 
    }

    // Permite mostrar las encuestas en el navegador
    @GetMapping("/encuestas")
    public List<EncuestaModel> mostrar(){
       return service.traerTodos();
    }
 
    //Metodo para actualizar la encuesta
     @PutMapping("/encuestas")
     public ResponseEntity<Map<String, String>> actualizar(@Valid @RequestBody EncuestaModel encuesta){
         this.service.guardarEncuesta(encuesta); //Invocamos el metodo creado en el servicio
         Map<String, String> respuesta=new HashMap<>();//Creamos el map para la respuesta al cliente
         respuesta.put("mensaje", "Se actualizó la encuesta correctamente"); //Se agrega la respuesta que se quiere enviar
         respuesta.put("estado", "true");
         return ResponseEntity.ok(respuesta); 
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
