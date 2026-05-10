package com.QuickOrder360.notificacion.service;

import com.QuickOrder360.exception.ResourceNotFoundException;
import com.QuickOrder360.notificacion.model.Notificacion;
import com.QuickOrder360.notificacion.repository.NotificacionRepository;
import com.QuickOrder360.usuario.model.Usuario;
import com.QuickOrder360.usuario.repository.UsuarioRepository;
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
    private UsuarioRepository usuarioRepository;

    public List<Notificacion> findAll() {
        return notificacionRepository.findAll();
    }

    public List<Notificacion> findByUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuarioId(usuarioId);
    }

    public List<Notificacion> findNoLeidas(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdAndLeidoFalse(usuarioId);
    }

    public Notificacion save(Notificacion notificacion) {
        log.info("Creando notificación");
        if (notificacion.getUsuario() == null || notificacion.getUsuario().getId() == null) {
            throw new com.QuickOrder360.exception.BadRequestException("Debe especificar un usuario para la notificación");
        }
        
        Usuario usuarioDB = usuarioRepository.findById(notificacion.getUsuario().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", notificacion.getUsuario().getId()));
        
        notificacion.setUsuario(usuarioDB);
        return notificacionRepository.save(notificacion);
    }

    public void marcarComoLeida(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificacion", id));
        notificacion.setLeido(true);
        notificacionRepository.save(notificacion);
    }

}
