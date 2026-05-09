package com.QuickOrder360.despacho.controller;

import com.QuickOrder360.despacho.model.Despacho;
import com.QuickOrder360.despacho.service.DespachoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/despachos")
public class DespachoController {

    @Autowired
    private DespachoService despachoService;

    @GetMapping
    public ResponseEntity<List<Despacho>> listar() {
        List<Despacho> despachos = despachoService.findAll();
        if (despachos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(despachos);
    }

    @PostMapping
    public ResponseEntity<Despacho> guardar(@Valid @RequestBody Despacho despacho) {
        Despacho despachoNuevo = despachoService.save(despacho);
        return ResponseEntity.status(HttpStatus.CREATED).body(despachoNuevo);
    }
}
