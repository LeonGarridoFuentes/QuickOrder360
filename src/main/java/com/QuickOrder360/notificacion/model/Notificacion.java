package com.QuickOrder360.notificacion.model;

import com.QuickOrder360.usuario.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull
    private Usuario usuario;

    @Column(nullable = false)
    @NotBlank
    private String mensaje;

    @Column(nullable = false)
    @NotBlank
    private String tipo; // INFO, ALERTA, EXITO

    @Column(nullable = false)
    private Boolean leido;

    private LocalDateTime fechaEnvio;

    @PrePersist
    protected void onCreate() {
        fechaEnvio = LocalDateTime.now();
        if (leido == null) {
            leido = false;
        }
    }
}