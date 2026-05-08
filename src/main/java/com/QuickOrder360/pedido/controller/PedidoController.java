package com.QuickOrder360.pedido.controller;

import com.QuickOrder360.cliente.model.Cliente;
import com.QuickOrder360.cliente.service.ClienteService;
import com.QuickOrder360.cliente.repository.ClienteRepository;
import com.QuickOrder360.producto.model.Producto;
import com.QuickOrder360.producto.service.ProductoService;
import com.QuickOrder360.pedido.model.Pedido;
import com.QuickOrder360.pedido.service.PedidoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

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
    public  ResponseEntity<List<Pedido>> listar(){
        List<Pedido> pedidos = pedidoService.findAll();
        if (pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody Pedido pedido){

        if(pedido.getCliente() == null){
            return ResponseEntity.badRequest().body("Debe ingresar un cliente");
        }
        Cliente clienteDB = clienteService.findById(pedido.getCliente().getId());

        if(clienteDB == null){
            return ResponseEntity.badRequest().body("Cliente no encontrado");
        }
        pedido.setCliente(clienteDB);

        if(pedido.getProductos() == null || pedido.getProductos().isEmpty()){
            return ResponseEntity.badRequest().body("Debe ingresar productos");
        }
        int total = 0;

        List<Producto> productosCompletos = new ArrayList<>();

        for(Producto producto : pedido.getProductos()){

            Producto productoDB = productoService.findById(producto.getId());

            if(productoDB == null){
                return ResponseEntity.badRequest().body("Producto no encontrado");
            }
            total += productoDB.getPrecio();
            productosCompletos.add(productoDB);
        }

        pedido.setProductos(productosCompletos);
        pedido.setPrecioTotal(total);
        Pedido pedidoNuevo = pedidoService.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoNuevo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try {
            pedidoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}

//l
