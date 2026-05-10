package com.QuickOrder360.notificacion.service;

import com.QuickOrder360.cliente.model.Cliente;
import com.QuickOrder360.cliente.repository.ClienteRepository;
import com.QuickOrder360.exception.ResourceNotFoundException;
import com.QuickOrder360.notificacion.model.Notificacion;
import com.QuickOrder360.notificacion.repository.NotificacionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Notificacion> findAll() {
        return notificacionRepository.findAll();
    }

    public List<Notificacion> findByCliente(Long clienteId) {
        return notificacionRepository.findByClienteId(clienteId);
    }

    public List<Notificacion> findNoLeidas(Long clienteId) {
        return notificacionRepository.findByClienteIdAndLeidoFalse(clienteId);
    }

    public Notificacion save(Notificacion notificacion) {
        log.info("Creando notificación");

        if (notificacion.getCliente() == null || notificacion.getCliente().getId() == null) {
            throw new com.QuickOrder360.exception.BadRequestException(
                    "Debe especificar un cliente para la notificación");
        }

        Cliente clienteDB = clienteRepository.findById(notificacion.getCliente().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cliente",
                        notificacion.getCliente().getId()));

        notificacion.setCliente(clienteDB);

        return notificacionRepository.save(notificacion);
    }

    public void marcarComoLeida(Long id) {

        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificacion", id));

        notificacion.setLeido(true);

        notificacionRepository.save(notificacion);
    }
}