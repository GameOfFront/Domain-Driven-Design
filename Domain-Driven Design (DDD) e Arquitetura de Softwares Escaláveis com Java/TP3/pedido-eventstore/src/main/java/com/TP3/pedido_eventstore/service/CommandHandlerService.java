package com.TP3.pedido_eventstore.service;

import com.TP3.pedido_eventstore.commands.CriarPedidoCommand;
import com.TP3.pedido_eventstore.events.PedidoCriadoEvent;
import com.TP3.pedido_eventstore.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável por tratar comandos e transformá-los em eventos.
 */
@Service
public class CommandHandlerService {

    private final EventRepository eventRepository;

    public CommandHandlerService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Processa o comando CriarPedidoCommand e gera um PedidoCriadoEvent persistido no banco.
     */
    @Transactional
    public PedidoCriadoEvent handle(CriarPedidoCommand command) {
        PedidoCriadoEvent event = new PedidoCriadoEvent(
                command.getAggregateId(),
                command.getDescricao()
        );

        return eventRepository.save(event);
    }
}
