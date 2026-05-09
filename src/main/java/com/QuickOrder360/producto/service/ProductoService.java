package com.QuickOrder360.producto.service;

import com.QuickOrder360.exception.ResourceNotFoundException;
import com.QuickOrder360.producto.model.Producto;
import com.QuickOrder360.producto.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductoService {

    private static final Logger log = LoggerFactory.getLogger(ProductoService.class);

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        log.info("Buscando todos los productos");
        return productoRepository.findAll();
    }

    public Producto save(Producto producto) {
        log.info("Guardando nuevo producto");
        return productoRepository.save(producto);
    }

    public void delete(Long id) {
        log.info("Eliminando producto por ID: {}", id);
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto", id);
        }
        productoRepository.deleteById(id);
    }

    public Producto findById(Long id) {
        log.info("Buscando producto por ID: {}", id);
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
    }
}