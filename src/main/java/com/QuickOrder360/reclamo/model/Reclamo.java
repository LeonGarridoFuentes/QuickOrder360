package com.QuickOrder360.reclamo.model;

import com.QuickOrder360.pedido.model.Pedido;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "reclamo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    @NotNull
    private Pedido pedido;

    @Column(nullable = false)
    @NotBlank
    private String motivo;

    @Column(nullable = false, length = 1000)
    @NotBlank
    private String descripcion;

    @Column(nullable = false)
    @NotBlank
    private String estado;

    private LocalDateTime fechaReclamo;

    @PrePersist
    protected void onCreate() {
        fechaReclamo = LocalDateTime.now();
    }
}
