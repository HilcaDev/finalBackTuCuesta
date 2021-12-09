package com.tuCuesta.encuestas.controllers;

import java.security.Provider.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.tuCuesta.encuestas.exceptions.CustomException;
import com.tuCuesta.encuestas.models.UsuarioModel;
import com.tuCuesta.encuestas.services.UsuarioService;
import com.tuCuesta.encuestas.utils.Autorizacion;
import com.tuCuesta.encuestas.utils.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@CrossOrigin(origins = "*") // Para que se pueda conectar con el frontend y acepte cualquier cliente
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    //Verifica si el token esta activo
    @GetMapping("/verificar")
    public ResponseEntity<Map<String,Boolean>> verificarToken(){
        Map<String,Boolean> respuesta  = new HashMap<>();
        respuesta.put("ok", true);
        return ResponseEntity.ok(respuesta);
    }
 


    //Meetodo para agregar un usuario
    @PostMapping("/usuarios")
    public ResponseEntity<Map<String,String>> guardarUsuarios(@RequestBody UsuarioModel usuario){
       
        Map<String,String> respuesta = new HashMap<>();
        
        //Linea que hace el cifrado de la contrasena con la clase BCryp
        usuario.setPassword(BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt()));

        UsuarioModel u = this.usuarioService.buscarPorUsername(usuario.getUsername());
        if(u.getId()==null){
            this.usuarioService.guardar(usuario);
            respuesta.put("mensaje", "El usuario se agregó correctamente");
        } else{

            respuesta.put("mensaje", "El usuario ya esta registrado");
        }

        return ResponseEntity.ok(respuesta);
    }

    // Metodo login de usuarios
    @PostMapping("/usuarios/login")
    public ResponseEntity<Map<String,String>> acceder(@RequestBody UsuarioModel usuario){

        // Objeto auxiliar del tipo usuarioModel
        UsuarioModel auxiliar = this.usuarioService.buscarPorUsername(usuario.getUsername());

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
                String hash="";
                Long tiempo = System.currentTimeMillis();
    
                // Ahora ademas del mensaje  nos debe enviar el TOKEN de autenticacion
                if(auxiliar.getId() != null){
                    hash=Jwts.builder()
                        .signWith(SignatureAlgorithm.HS256, Autorizacion.KEY)
                        .setSubject(auxiliar.getNombre())
                        .setIssuedAt(new Date(tiempo))
                        .setExpiration(new Date(tiempo + 900000))    
                        .claim("username", auxiliar.getUsername())   
                        .claim("correo", auxiliar.getCorreo())       
                        .compact(); 
                }

                auxiliar.setHash(hash);
                respuesta.put("mensaje","Se accedió correctamente");
                respuesta.put("token",hash);
                respuesta.put("id",auxiliar.getId());
                respuesta.put("nombre",auxiliar.getNombre());
                respuesta.put("correo",auxiliar.getCorreo());
                respuesta.put("username",auxiliar.getUsername());
                

            }
        }

        return ResponseEntity.ok(respuesta);

    }

    @PutMapping("/usuarios") //POST 
    public ResponseEntity<Map<String, String>> actualizarUsuario(@RequestBody UsuarioModel usuario){
        
        //Map tener una clave valor {"mensaje": "Se agregó correctamente"}
        Map<String, String> respuesta= new HashMap<>();
            this.usuarioService.guardar(usuario); //Actualizo al usuario
            respuesta.put("mensaje","Se actualizó correctamente");

        return ResponseEntity.ok(respuesta);
    }

}

   
