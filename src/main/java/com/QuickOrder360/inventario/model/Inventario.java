package com.QuickOrder360.inventario.model;

import com.QuickOrder360.producto.model.Producto;
import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Inventario {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "inventario_producto",
            joinColumns = @JoinColumn(name = "inventario_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    @NotNull
    private List<Producto> productos = new ArrayList<>();

}
//l