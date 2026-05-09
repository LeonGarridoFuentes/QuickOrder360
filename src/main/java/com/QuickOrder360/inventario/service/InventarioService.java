package com.QuickOrder360.inventario.service;

import com.QuickOrder360.inventario.model.Inventario;
import com.QuickOrder360.inventario.repository.InventarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> findAll(){
        return inventarioRepository.findAll();
    }

    public Inventario save(Inventario inventario){return inventarioRepository.save(inventario);}

    public void delete(Long id){
        inventarioRepository.deleteById(id);
    }
}

//l