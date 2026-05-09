package com.QuickOrder360.pago.service;

import com.QuickOrder360.exception.BadRequestException;
import com.QuickOrder360.pago.model.Pago;
import com.QuickOrder360.pago.repository.PagoRepository;
import com.QuickOrder360.pedido.model.Pedido;
import com.QuickOrder360.pedido.service.PedidoService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PagoService {

    private static final Logger log = LoggerFactory.getLogger(PagoService.class);

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PedidoService pedidoService;

    public List<Pago> findAll() {
        log.info("Buscando todos los pagos");
        return pagoRepository.findAll();
    }

    public Pago save(Pago pago) {
        log.info("Procesando nuevo pago");

        if (pago.getPedido() == null || pago.getPedido().getId() == null) {
            throw new BadRequestException("Debe especificar un pedido con ID válido para realizar el pago");
        }

        Pedido pedidoDB = pedidoService.findById(pago.getPedido().getId());

        // Regla de Negocio: El pago no puede ser inferior al total del pedido
        if (pago.getMonto() < pedidoDB.getPrecioTotal()) {
            throw new BadRequestException("El monto del pago (" + pago.getMonto() + 
                ") no puede ser inferior al total del pedido (" + pedidoDB.getPrecioTotal() + ")");
        }

        pago.setPedido(pedidoDB);
        
        // Si el monto es igual o mayor, aprobamos el pago (podría haber lógica de vuelto aquí)
        pago.setEstado("APROBADO");

        log.info("Pago aprobado para el pedido ID: {}", pedidoDB.getId());
        return pagoRepository.save(pago);
    }

    public boolean isPagoAprobado(Long pedidoId) {
        return pagoRepository.findByPedidoId(pedidoId)
                .map(pago -> "APROBADO".equals(pago.getEstado()))
                .orElse(false);
    }
}
