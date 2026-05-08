package com.QuickOrder360.producto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QuickOrder360.producto.model.Producto;
import com.QuickOrder360.producto.repository.ProductoRepository;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

    public void delete(Long id){
        productoRepository.deleteById(id);
    }

    public Producto findById(Long id){
        return productoRepository.findById(id).orElse(null);
    }

}
//s