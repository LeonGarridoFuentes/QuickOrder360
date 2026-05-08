package com.QuickOrder360.usuario.service;

import com.QuickOrder360.usuario.model.Usuario;
import com.QuickOrder360.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public  Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public void delete(Long id){
        usuarioRepository.deleteById(id);
    }
}

//l