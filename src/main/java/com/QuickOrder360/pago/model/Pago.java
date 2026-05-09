package com.QuickOrder360.pago.model;

import com.QuickOrder360.pedido.model.Pedido;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false, unique = true)
    @NotNull
    private Pedido pedido;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    private Integer monto;

    @Column(nullable = false)
    @NotNull
    private String metodoPago; // Podría ser un Enum: EFECTIVO, TARJETA, TRANSFERENCIA

    @Column(nullable = false)
    @NotNull
    private String estado; // Podría ser un Enum: PENDIENTE, APROBADO, RECHAZADO

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaPago;

    @PrePersist
    protected void onCreate() {
        fechaPago = LocalDateTime.now();
    }
}
