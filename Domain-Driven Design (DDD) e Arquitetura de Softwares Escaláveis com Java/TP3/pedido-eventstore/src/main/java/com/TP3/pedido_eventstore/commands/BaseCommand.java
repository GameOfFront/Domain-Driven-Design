package com.TP3.pedido_eventstore.commands;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

/**
 * Classe base abstrata para comandos do domínio (padrão CQRS).
 * Representa uma intenção do sistema (ex: criar pedido).
 * Quando processado, o comando resulta na geração de um evento.
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class BaseCommand {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private Instant issuedAt = Instant.now();

    @Column(nullable = false)
    private String commandType;

    @Column(nullable = false)
    private String aggregateId;

    public BaseCommand(String commandType, String aggregateId) {
        this.commandType = commandType;
        this.aggregateId = aggregateId;
        this.issuedAt = Instant.now();
    }

    /**
     * Garante que campos obrigatórios sejam preenchidos antes da persistência.
     */
    @PrePersist
    protected void onPrePersist() {
        if (this.commandType == null || this.commandType.isBlank()) {
            this.commandType = this.getClass().getSimpleName();
        }
        if (this.issuedAt == null) {
            this.issuedAt = Instant.now();
        }
    }
}
