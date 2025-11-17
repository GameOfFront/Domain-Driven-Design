package com.TP3.pedido_eventstore.commands;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

/**
 * Command responsável por solicitar a criação de um novo pedido.
 * Quando executado, irá gerar o evento PedidoCriadoEvent.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CriarPedidoCommand extends BaseCommand {

    @Column(nullable = false)
    private String descricao;

    public CriarPedidoCommand(String aggregateId, String descricao) {
        super("CriarPedidoCommand", aggregateId);
        this.descricao = descricao;
    }
}
