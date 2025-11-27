package com.petfriends.transporte.domain.repository;

import com.petfriends.transporte.domain.entity.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    Optional<Entrega> findByCodigoEntrega(String codigoEntrega);
    Optional<Entrega> findByCodigoPedido(String codigoPedido);
}
