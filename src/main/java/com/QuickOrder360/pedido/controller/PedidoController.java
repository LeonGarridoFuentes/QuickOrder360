package com.QuickOrder360.pedido.controller;

import com.QuickOrder360.cliente.model.Cliente;
import com.QuickOrder360.cliente.service.ClienteService;
import com.QuickOrder360.exception.BadRequestException;
import com.QuickOrder360.exception.ResourceNotFoundException;
import com.QuickOrder360.producto.model.Producto;
import com.QuickOrder360.producto.service.ProductoService;
import com.QuickOrder360.pedido.model.Pedido;
import com.QuickOrder360.pedido.service.PedidoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> pedidos = pedidoService.findAll();
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody Pedido pedido) {

        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
            throw new BadRequestException("Debe especificar un cliente con su ID en el pedido");
        }
        Cliente clienteDB = clienteService.findById(pedido.getCliente().getId());
        pedido.setCliente(clienteDB);

        if (pedido.getProductos() == null || pedido.getProductos().isEmpty()) {
            throw new BadRequestException("Debe ingresar mínimo un producto en el pedido");
        }

        int total = 0;
        List<Producto> productosCompletos = new ArrayList<>();

        for (Producto producto : pedido.getProductos()) {
            if (producto.getId() == null) {
                throw new BadRequestException("Cada producto debe tener un ID válido");
            }

            Producto productoDB = productoService.findById(producto.getId());
            total += productoDB.getPrecio();
            productosCompletos.add(productoDB);
        }

        pedido.setProductos(productosCompletos);
        pedido.setPrecioTotal(total);

        Pedido pedidoNuevo = pedidoService.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoNuevo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
