package com.QuickOrder360.pedido.controller;

import com.QuickOrder360.cliente.model.Cliente;
import com.QuickOrder360.cliente.repository.ClienteRepository;
import com.QuickOrder360.cliente.service.ClienteService;
import com.QuickOrder360.exception.BadRequestException;
import com.QuickOrder360.producto.model.Producto;
import com.QuickOrder360.producto.service.ProductoService;
import com.QuickOrder360.inventario.model.Inventario;
import com.QuickOrder360.inventario.service.InventarioService;
import com.QuickOrder360.pedido.model.DetallePedido;
import com.QuickOrder360.pedido.model.Pedido;
import com.QuickOrder360.pedido.service.PedidoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private InventarioService inventarioService;

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

        if (pedido.getDetalles() == null || pedido.getDetalles().isEmpty()) {
            throw new BadRequestException("Debe ingresar mínimo un detalle (producto y cantidad) en el pedido");
        }

        int total = 0;

        for (DetallePedido detalle : pedido.getDetalles()) {
            if (detalle.getProducto() == null || detalle.getProducto().getId() == null) {
                throw new BadRequestException("Cada detalle debe tener un producto con ID válido");
            }

            Producto productoDB = productoService.findById(detalle.getProducto().getId());

            if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
                throw new BadRequestException(
                        "La cantidad debe ser mayor a 0 para el producto: " + productoDB.getNombreProducto());
            }

            Inventario inventario = inventarioService.findByProductoId(productoDB.getId());
            if (inventario.getStock() < detalle.getCantidad()) {
                throw new BadRequestException(
                        "No hay stock suficiente para el producto: " + productoDB.getNombreProducto()
                                + ". Stock actual: " + inventario.getStock());
            }

            detalle.setProducto(productoDB);
            detalle.setPrecioUnitario(productoDB.getPrecio());
            detalle.setSubtotal(productoDB.getPrecio() * detalle.getCantidad());
            detalle.setPedido(pedido);

            total += detalle.getSubtotal();
        }

        pedido.setPrecioTotal(total);

        Pedido pedidoNuevo = pedidoService.save(pedido);

        for (DetallePedido detalle : pedidoNuevo.getDetalles()) {
            inventarioService.descontarStock(detalle.getProducto().getId(), detalle.getCantidad());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoNuevo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
