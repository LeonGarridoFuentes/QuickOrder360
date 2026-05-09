package com.QuickOrder360.despacho.model;

import com.QuickOrder360.pedido.model.Pedido;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "despacho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Despacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    @NotNull
    private Pedido pedido;

    @Column(nullable = false)
    @NotBlank
    private String direccionEnvio;

    @Column(nullable = false)
    @NotBlank
    private String estado; // PENDIENTE, EN_CAMINO, ENTREGADO

    private LocalDateTime fechaSalida;

    private LocalDateTime fechaEntregaEstimada;
}
