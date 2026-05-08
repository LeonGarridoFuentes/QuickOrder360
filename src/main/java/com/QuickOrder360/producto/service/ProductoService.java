package com.QuickOrder360.producto.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QuickOrder360.producto.model.Producto;
import com.QuickOrder360.producto.repository.ProductoRepository;

@Service
public class ProductoService {

    private static final Logger log = LoggerFactory.getLogger(ProductoService.class);
    
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll(){
        log.info("Buscando todos los productos");
        return productoRepository.findAll();
    }

    public Producto save(Producto producto){
        log.info("Guardando nuevo producto");
        return productoRepository.save(producto);
    }

    public void delete(Long id){
        log.info("Eliminando producto por ID: {}", id);
        productoRepository.deleteById(id);
    }

    public Producto findById(Long id){
        return productoRepository.findById(id).orElse(null);
    }

}
//s