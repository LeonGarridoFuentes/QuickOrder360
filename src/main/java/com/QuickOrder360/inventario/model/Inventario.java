package com.QuickOrder360.inventario.model;

import com.QuickOrder360.producto.model.Producto;
import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import jakarta.validation.constraints.Min;

@Entity
@Table(name = "inventario")
@Data
public class Inventario {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "producto_id", nullable = false, unique = true)
    @NotNull
    private Producto producto;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer stock;

}
//l