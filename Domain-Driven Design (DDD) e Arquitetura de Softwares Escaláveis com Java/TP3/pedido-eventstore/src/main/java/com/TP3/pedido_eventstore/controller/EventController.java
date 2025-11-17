package com.TP3.pedido_eventstore.controller;

import com.TP3.pedido_eventstore.events.PedidoCriadoEvent;
import com.TP3.pedido_eventstore.service.QueryService;
import com.TP3.pedido_eventstore.repository.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador responsável por expor os endpoints de criação e consulta de eventos.
 * Segue o padrão CQRS, utilizando serviços separados para leitura e escrita.
 */
@RestController
@RequestMapping("/api/eventos")
public class EventController {

    private final EventRepository repository;
    private final QueryService queryService;

    public EventController(EventRepository repository, QueryService queryService) {
        this.repository = repository;
        this.queryService = queryService;
    }

    /**
     * Endpoint responsável por criar e persistir um novo evento.
     * Exemplo de uso: POST /api/eventos
     */
    @PostMapping
    public PedidoCriadoEvent criarEvento(@RequestBody PedidoCriadoEvent evento) {
        return repository.save(evento);
    }

    /**
     * Endpoint responsável por consultar todos os eventos persistidos.
     * Exemplo de uso: GET /api/eventos
     */
    @GetMapping
    public List<PedidoCriadoEvent> listarEventos() {
        return queryService.listarTodosEventos();
    }

    /**
     * Endpoint responsável por buscar um evento específico pelo ID.
     * Exemplo de uso: GET /api/eventos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PedidoCriadoEvent> buscarPorId(@PathVariable UUID id) {
        return queryService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
