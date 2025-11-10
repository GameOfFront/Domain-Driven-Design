package org.example.domain.pedidos;

import org.example.domain.pedidos.events.PedidoConfirmadoEvent;
import org.example.domain.shared.DomainEventPublisher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pedido {

    private Long id;
    private Long clienteId;
    private LocalDateTime dataCriacao;
    private StatusPedido status;
    private List<ItemPedido> itens = new ArrayList<>();

    public Pedido(Long clienteId) {
        this.clienteId = Objects.requireNonNull(clienteId, "Cliente ID não pode ser nulo");
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusPedido.CRIADO;
    }

    public void adicionarItem(Long produtoId, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
        itens.add(new ItemPedido(produtoId, quantidade));
    }

    public void confirmar() {
        if (itens.isEmpty()) {
            throw new IllegalStateException("Pedido não pode ser confirmado sem itens");
        }

        this.status = StatusPedido.CONFIRMADO;

        PedidoConfirmadoEvent evento = new PedidoConfirmadoEvent(this.id, this.clienteId);
        DomainEventPublisher.instance().publish(evento);
    }

    public Long getClienteId() { return clienteId; }
    public List<ItemPedido> getItens() { return List.copyOf(itens); }
    public StatusPedido getStatus() { return status; }

    @Override
    public String toString() {
        return "Pedido{" +
                "clienteId=" + clienteId +
                ", status=" + status +
                ", itens=" + itens.size() +
                '}';
    }
}
