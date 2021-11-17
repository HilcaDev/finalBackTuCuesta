package com.tuCuesta.encuestas.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "encuestas")
public class EncuestaModel {
    
    @Id
    private String id;
    private String fecha;
    private int votoscandidato1;
    private int votoscandidato2;
    private CandidatoModel candidato1;
    private CandidatoModel candidato2;

    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public int getVotoscandidato1() {
        return votoscandidato1;
    }
    public void setVotoscandidato1(int votoscandidato1) {
        this.votoscandidato1 = votoscandidato1;
    }
    public int getVotoscandidato2() {
        return votoscandidato2;
    }
    public void setVotoscandidato2(int votoscandidato2) {
        this.votoscandidato2 = votoscandidato2;
    }
    public CandidatoModel getCandidato1() {
        return candidato1;
    }
    public void setLocal(CandidatoModel candidato1) {
        this.candidato1 = candidato1;
    }
    public CandidatoModel getCandidato2() {
        return candidato2;
    }
    public void setVisitante(CandidatoModel candidato2) {
        this.candidato2=candidato2; 
    }
}
