package com.TP3.pedido_eventstore.aggregates;

import com.TP3.pedido_eventstore.commands.CriarPedidoCommand;
import com.TP3.pedido_eventstore.events.PedidoCriadoEvent;

/**
 * Agregado de domínio que representa o Pedido.
 * Contém a lógica de manipulação de comandos e aplicação de eventos (Event Sourcing).
 */
public class PedidoAggregate {

    private String aggregateId;
    private String descricao;
    private boolean criado;

    /**
     * Manipula o comando de criação de pedido e gera o evento correspondente.
     */
    public PedidoCriadoEvent handle(CriarPedidoCommand command) {
        if (criado) {
            throw new IllegalStateException("O pedido já foi criado anteriormente.");
        }

        return new PedidoCriadoEvent(command.getAggregateId(), command.getDescricao());
    }

    /**
     * Event Sourcing Handler:
     * Aplica o evento de criação de pedido e atualiza o estado interno do agregado.
     */
    public void apply(PedidoCriadoEvent event) {
        this.aggregateId = event.getAggregateId();
        this.descricao = event.getDescricao();
        this.criado = true;
    }

    // Getters apenas para visualização (não obrigatórios, mas úteis)
    public String getAggregateId() {
        return aggregateId;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isCriado() {
        return criado;
    }
}
