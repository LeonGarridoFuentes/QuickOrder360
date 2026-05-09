package com.QuickOrder360.pedido.model;

import com.QuickOrder360.cliente.model.Cliente;
import com.QuickOrder360.producto.model.Producto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull
    private List<DetallePedido> detalles;

    @Column(nullable = false)
    @NotNull
    private Integer precioTotal;
}

// l