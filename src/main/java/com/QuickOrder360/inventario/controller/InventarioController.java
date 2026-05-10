package com.QuickOrder360.inventario.controller;

import com.QuickOrder360.inventario.model.Inventario;
import com.QuickOrder360.inventario.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventarios")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<Inventario>> listar() {
        List<Inventario> inventarios = inventarioService.findAll();
        if (inventarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(inventarios);
    }

    @PostMapping
    public ResponseEntity<Inventario> guardar(@RequestBody Inventario inventario) {
        if (inventario.getProducto() == null || inventario.getProducto().getId() == null) {
            throw new com.QuickOrder360.exception.BadRequestException("Debe especificar un producto con su ID");
        }
        if (inventario.getStock() == null || inventario.getStock() < 0) {
            throw new com.QuickOrder360.exception.BadRequestException(
                    "El stock debe ser un valor válido mayor o igual a 0");
        }
        Inventario inventarioNuevo = inventarioService.save(inventario);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioNuevo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}