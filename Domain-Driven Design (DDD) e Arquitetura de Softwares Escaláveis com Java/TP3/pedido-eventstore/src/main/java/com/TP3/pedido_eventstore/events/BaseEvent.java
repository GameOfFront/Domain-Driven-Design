package com.TP3.pedido_eventstore.events;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

/**
 * Classe base abstrata para eventos de domínio.
 * Todos os eventos (ex: PedidoCriadoEvent) herdam desta classe.
 * Essa classe será persistida em um Event Store.
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class BaseEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private String aggregateId;

    // Construtor auxiliar usado pelas subclasses (ex: PedidoCriadoEvent)
    public BaseEvent(String eventType, String aggregateId) {
        this.eventType = eventType;
        this.aggregateId = aggregateId;
        this.createdAt = Instant.now();
    }

    /**
     * Executado automaticamente antes da persistência.
     * Garante que os campos obrigatórios tenham valores válidos.
     */
    @PrePersist
    protected void onPrePersist() {
        if (this.eventType == null || this.eventType.isBlank()) {
            this.eventType = this.getClass().getSimpleName();
        }
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }
}
