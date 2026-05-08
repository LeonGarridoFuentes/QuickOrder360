package com.QuickOrder360.pedido.repository;

import com.QuickOrder360.pedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{

    void deleteById(Integer id);


}
