package com.tuCuesta.encuestas.controllers;

import java.security.Provider.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.tuCuesta.encuestas.exceptions.CustomException;
import com.tuCuesta.encuestas.models.UsuarioModel;
import com.tuCuesta.encuestas.services.UsuarioService;
import com.tuCuesta.encuestas.utils.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<Map<String,String>> guardar(@Valid @RequestBody UsuarioModel usuario, Errors error){
       
        if(error.hasErrors()){ // Hay un error, evita que se guarden datos nulos en la BD
            throwError(error);
        }

        Map<String,String> respuesta = new HashMap<>();
        
        //Linea que hace el cifrado de la contrasena con la clase BCryp
        usuario.setPassword(BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt()));

        UsuarioModel u = this.usuarioService.buscarUsername(usuario.getUsername());
        if(u.getId()==null){
            this.usuarioService.guardarUsuario(usuario);
            respuesta.put("mensaje", "El usuario se agregó correctamente");
        } else{

            respuesta.put("mensaje", "El usuario ya esta registrado");
        }

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/usuarios")
    public List<UsuarioModel> mostrar(){
    return usuarioService.traerTodos();
    }

    // Metodo login de usuarios
    @PostMapping("/usuarios/login")
    public ResponseEntity<Map<String,String>> acceder(@RequestBody UsuarioModel usuario){

        // Objeto auxiliar del tipo usuarioModel
        UsuarioModel auxiliar = this.usuarioService.buscarUsername(usuario.getUsername());

        // Map para el mensaje
        Map<String,String> respuesta = new HashMap<>();

        // Condiciones de acceso
        // Username no debe estar vacio
        if(auxiliar.getUsername() == null){
            respuesta.put("mensaje", "Usuario o contraseña incorrectos");
        } else {
            // contrasenas diferentes
            if(!BCrypt.checkpw(usuario.getPassword(), auxiliar.getPassword())){
                respuesta.put("mensaje", "Usuario o contraseña incorrectos");
            
            } else{
                // contrasenas iguales
                respuesta.put("mensaje", "Se accedió correctamente");
                
            }
        }

        return ResponseEntity.ok(respuesta);

    }

    //Método para el manejo de errores
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
