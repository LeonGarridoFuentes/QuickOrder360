package com.QuickOrder360.Inventario.service;

import com.QuickOrder360.Inventario.model.Inventario;
import com.QuickOrder360.Inventario.repository.InventarioRepository;
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