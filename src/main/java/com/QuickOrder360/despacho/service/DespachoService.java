package com.QuickOrder360.despacho.service;

import com.QuickOrder360.despacho.model.Despacho;
import com.QuickOrder360.despacho.repository.DespachoRepository;
import com.QuickOrder360.exception.BadRequestException;
import com.QuickOrder360.pago.service.PagoService;
import com.QuickOrder360.pedido.model.Pedido;
import com.QuickOrder360.pedido.service.PedidoService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class DespachoService {

    private static final Logger log = LoggerFactory.getLogger(DespachoService.class);

    @Autowired
    private DespachoRepository despachoRepository;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PedidoService pedidoService;

    public List<Despacho> findAll() {
        log.info("Buscando todos los despachos");
        return despachoRepository.findAll();
    }

    public Despacho save(Despacho despacho) {
        log.info("Registrando nuevo despacho");

        if (despacho.getPedido() == null || despacho.getPedido().getId() == null) {
            throw new BadRequestException("Debe especificar un pedido con ID válido para el despacho");
        }

        Long pedidoId = despacho.getPedido().getId();

        // Un despacho solo puede generarse si el pago fue aprobado
        if (!pagoService.isPagoAprobado(pedidoId)) {
            throw new BadRequestException(
                    "No se puede generar el despacho: El pago del pedido " + pedidoId + " no ha sido aprobado.");
        }

        Pedido pedidoDB = pedidoService.findById(pedidoId);
        despacho.setPedido(pedidoDB);

        if (despacho.getEstado() == null) {
            despacho.setEstado("PENDIENTE");
        }

        if (despacho.getFechaEntregaEstimada() == null) {
            despacho.setFechaEntregaEstimada(LocalDateTime.now().plusDays(3)); // 3 días por defecto
        }

        return despachoRepository.save(despacho);
    }
}
