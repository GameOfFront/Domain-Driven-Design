package org.example.domain.pedidos.events;

import org.example.domain.shared.DomainEvent;

public class PedidoConfirmadoEvent extends DomainEvent {

    private final Long pedidoId;
    private final Long clienteId;

    public PedidoConfirmadoEvent(Long pedidoId, Long clienteId) {
        super();
        this.pedidoId = pedidoId;
        this.clienteId = clienteId;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    @Override
    public String toString() {
        return super.toString() + " PedidoConfirmadoEvent{" +
                "pedidoId=" + pedidoId +
                ", clienteId=" + clienteId +
                '}';
    }
}
