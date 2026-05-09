package com.QuickOrder360.inventario.repository;

import com.QuickOrder360.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long>{

    void deleteById(Long id);

    java.util.Optional<Inventario> findByProductoId(Long productoId);

}

//l