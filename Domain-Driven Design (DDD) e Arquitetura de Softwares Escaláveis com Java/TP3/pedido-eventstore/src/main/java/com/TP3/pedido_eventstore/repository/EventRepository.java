package com.TP3.pedido_eventstore.repository;


import com.TP3.pedido_eventstore.events.PedidoCriadoEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * Repositório de eventos de pedidos.
 */
public interface EventRepository extends JpaRepository<PedidoCriadoEvent, UUID> {
}
