package com.tuCuesta.encuestas.models;

import javax.validation.constraints.NotEmpty;

import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "candidatos")
public class CandidatoModel {
    
    @Id
    private String id;

    @NotEmpty(message = "El nombre del candidato no puede estar vacio")
    private String nombre;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

}
