package com.QuickOrder360.pedido.service;

import com.QuickOrder360.exception.ResourceNotFoundException;
import com.QuickOrder360.pedido.model.Pedido;
import com.QuickOrder360.pedido.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PedidoService {

    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll(){
        log.info("Buscando todos los pedidos");
        return pedidoRepository.findAll();
    }

    public Pedido save(Pedido pedido){
        log.info("Guardando nuevo pedido");
        Pedido guardado = pedidoRepository.save(pedido);
        log.info("Pedido guardado con ID: {}", guardado.getId());
        return guardado;
    }

    public void delete(Long id){
        log.info("Eliminando pedido por ID: {}", id);
        if (!pedidoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido", id);
        }
        pedidoRepository.deleteById(id);
    }
}

//l
