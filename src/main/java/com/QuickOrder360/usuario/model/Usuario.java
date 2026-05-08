package com.QuickOrder360.usuario.model;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer ip;

}

//l