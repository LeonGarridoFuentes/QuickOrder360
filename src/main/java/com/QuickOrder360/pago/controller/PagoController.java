package com.QuickOrder360.pago.controller;

import com.QuickOrder360.pago.model.Pago;
import com.QuickOrder360.pago.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<Pago>> listar() {
        List<Pago> pagos = pagoService.findAll();
        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagos);
    }

    @PostMapping
    public ResponseEntity<Pago> guardar(@Valid @RequestBody Pago pago) {
        Pago pagoNuevo = pagoService.save(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoNuevo);
    }
}
