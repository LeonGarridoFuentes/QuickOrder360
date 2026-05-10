package com.QuickOrder360.notificacion.controller;

import com.QuickOrder360.notificacion.model.Notificacion;
import com.QuickOrder360.notificacion.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    public ResponseEntity<List<Notificacion>> listar() {
        return ResponseEntity.ok(notificacionService.findAll());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Notificacion>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(notificacionService.findByCliente(clienteId));
    }

    @GetMapping("/cliente/{clienteId}/no-leidas")
    public ResponseEntity<List<Notificacion>> listarNoLeidas(@PathVariable Long clienteId) {
        return ResponseEntity.ok(notificacionService.findNoLeidas(clienteId));
    }

    @PostMapping
    public ResponseEntity<Notificacion> guardar(@Valid @RequestBody Notificacion notificacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.save(notificacion));
    }

    @PatchMapping("/{id}/leer")
    public ResponseEntity<Void> marcarComoLeida(@PathVariable Long id) {
        notificacionService.marcarComoLeida(id);
        return ResponseEntity.noContent().build();
    }
}