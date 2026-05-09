package com.QuickOrder360.usuario.service;

import com.QuickOrder360.exception.ResourceNotFoundException;
import com.QuickOrder360.usuario.model.Usuario;
import com.QuickOrder360.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        log.info("Buscando todos los usuarios");
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {
        log.info("Guardando nuevo usuario");
        return usuarioRepository.save(usuario);
    }

    public void delete(Long id) {
        log.info("Eliminando usuario por ID: {}", id);
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario", id);
        }
        usuarioRepository.deleteById(id);
    }
}