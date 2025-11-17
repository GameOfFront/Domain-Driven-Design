package com.TP3.pedido_eventstore.events;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

/**
 * Evento que representa a criação de um Pedido.
 * É gerado a partir do comando CriarPedidoCommand e persistido no Event Store.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PedidoCriadoEvent extends BaseEvent {

    @Column(nullable = false)
    private String descricao;

    public PedidoCriadoEvent(String aggregateId, String descricao) {
        super("PedidoCriadoEvent", aggregateId);
        this.descricao = descricao;
    }
}
