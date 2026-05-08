package com.QuickOrder360.Inventario.repository;

import com.QuickOrder360.Inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long>{

    void deleteById(Long id);

}

//l