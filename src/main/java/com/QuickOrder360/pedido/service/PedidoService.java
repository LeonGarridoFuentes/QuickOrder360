package com.QuickOrder360.pedido.service;

import com.QuickOrder360.pedido.model.Pedido;
import com.QuickOrder360.pedido.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll(){
        return pedidoRepository.findAll();
    }

    public Pedido save(Pedido pedido){
        return pedidoRepository.save(pedido);
    }

    public void delete(Long id){
        pedidoRepository.deleteById(id);
    }
}
