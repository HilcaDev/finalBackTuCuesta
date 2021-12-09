package com.tuCuesta.encuestas.services;

import java.util.List;
import java.util.Optional;

import com.tuCuesta.encuestas.models.UsuarioModel;
import com.tuCuesta.encuestas.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    @Autowired
    UsuarioRepository usuarioRepository;

    // Metodo para guardar usuario
    public void guardar(UsuarioModel usuario){
        this.usuarioRepository.save(usuario);
    }

    // Metodo para listar todos los usuarios
    public List<UsuarioModel> traerUsuarios(){
        return this.usuarioRepository.findAll();
    }

    // Metodo para buscar ID
    public Optional<UsuarioModel> buscarPorId(String id){
        return this.usuarioRepository.findById(id);
    }

    // Metodo para buscar por username
    public UsuarioModel buscarPorUsername(String username){
        return this.usuarioRepository.findByUsername(username).orElse(new UsuarioModel());
    }
}
