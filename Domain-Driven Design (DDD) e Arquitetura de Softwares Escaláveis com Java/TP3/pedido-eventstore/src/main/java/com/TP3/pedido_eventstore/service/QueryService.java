package com.TP3.pedido_eventstore.service;

import com.TP3.pedido_eventstore.events.PedidoCriadoEvent;
import com.TP3.pedido_eventstore.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Serviço responsável por consultar os eventos armazenados no Event Store.
 * Implementa a camada de leitura (Query Side) do padrão CQRS.
 */
@Service
public class QueryService {

    private final EventRepository eventRepository;

    public QueryService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Retorna todos os eventos de pedidos registrados no banco de dados.
     */
    public List<PedidoCriadoEvent> listarTodosEventos() {
        return eventRepository.findAll();
    }

    /**
     * Busca um evento específico pelo seu identificador (UUID).
     *
     * @param id identificador único do evento
     * @return o evento correspondente, caso exista
     */
    public Optional<PedidoCriadoEvent> buscarPorId(UUID id) {
        return eventRepository.findById(id);
    }
}
