package com.QuickOrder360.usuario.repository;


import com.QuickOrder360.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    void deleteById(Long id);
}
//l