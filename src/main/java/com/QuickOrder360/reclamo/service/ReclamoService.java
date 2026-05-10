package com.QuickOrder360.reclamo.service;

import com.QuickOrder360.exception.BadRequestException;
import com.QuickOrder360.exception.ResourceNotFoundException;
import com.QuickOrder360.pedido.model.Pedido;
import com.QuickOrder360.pedido.service.PedidoService;
import com.QuickOrder360.reclamo.model.Reclamo;
import com.QuickOrder360.reclamo.repository.ReclamoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ReclamoService {

    private static final Logger log = LoggerFactory.getLogger(ReclamoService.class);

    @Autowired
    private ReclamoRepository reclamoRepository;

    @Autowired
    private PedidoService pedidoService;

    public List<Reclamo> findAll() {
        log.info("Buscando todos los reclamos");
        return reclamoRepository.findAll();
    }

    public Reclamo save(Reclamo reclamo) {
        log.info("Registrando nuevo reclamo");

        if (reclamo.getPedido() == null || reclamo.getPedido().getId() == null) {
            throw new BadRequestException("Debe especificar un pedido con ID válido para el reclamo");
        }

        // Los reclamos deben estar asociados a un pedido válido
        Pedido pedidoDB = pedidoService.findById(reclamo.getPedido().getId());

        reclamo.setPedido(pedidoDB);

        if (reclamo.getEstado() == null) {
            reclamo.setEstado("PENDIENTE");
        }

        return reclamoRepository.save(reclamo);
    }

    public Reclamo findById(Long id) {
        log.info("Buscando reclamo por ID: {}", id);
        return reclamoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reclamo", id));
    }
}
