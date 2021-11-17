package com.tuCuesta.encuestas.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.tuCuesta.encuestas.models.UsuarioModel;
import com.tuCuesta.encuestas.services.UsuarioService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    //Meetodo para agregar un usuario
    @PostMapping("/usuarios")
    public ResponseEntity<Map<String,String>> guardar(@Valid @RequestBody UsuarioModel usuario){
        Map<String,String> respuesta = new HashMap<>();

        UsuarioModel u = this.usuarioService.buscarUsername(usuario.getUsername());

        if(u.getId()==null){
            this.usuarioService.guardarUsuario(usuario);
            respuesta.put("mensaje", "El usuario se agrego correctamente");
        } else{

            respuesta.put("mensaje", "El usuario ya esta registrado");
        }

        return ResponseEntity.ok(respuesta);
    }
}
