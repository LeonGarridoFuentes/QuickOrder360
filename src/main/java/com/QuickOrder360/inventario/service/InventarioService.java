package com.QuickOrder360.inventario.service;

import com.QuickOrder360.exception.ResourceNotFoundException;
import com.QuickOrder360.inventario.model.Inventario;
import com.QuickOrder360.inventario.repository.InventarioRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class InventarioService {

    private static final Logger log = LoggerFactory.getLogger(InventarioService.class);

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> findAll() {
        log.info("Buscando todos los inventarios");
        return inventarioRepository.findAll();
    }

    public Inventario save(Inventario inventario) {
        log.info("Guardando nuevo inventario");
        return inventarioRepository.save(inventario);
    }

    public void delete(Long id) {
        log.info("Eliminando inventario por ID: {}", id);
        if (!inventarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inventario", id);
        }
        inventarioRepository.deleteById(id);
    }

    public Inventario findByProductoId(Long productoId) {
        return inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventario para el Producto", productoId));
    }

    public void descontarStock(Long productoId, Integer cantidad) {
        Inventario inventario = findByProductoId(productoId);
        if (inventario.getStock() < cantidad) {
            throw new com.QuickOrder360.exception.BadRequestException("Stock insuficiente para el producto con ID: " + productoId);
        }
        inventario.setStock(inventario.getStock() - cantidad);
        inventarioRepository.save(inventario);
    }
}

// l