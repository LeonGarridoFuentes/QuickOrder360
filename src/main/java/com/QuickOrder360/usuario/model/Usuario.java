package com.QuickOrder360.usuario.model;

import com.QuickOrder360.cliente.model.Cliente;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String username;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 6)
    private String password;

    @Column(nullable = false)
    @NotBlank
    @Email
    private String email;

    @Column(nullable = false)
    @NotBlank
    private String rol;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

}

// l