package com.petfriends.transporte.domain.entity;

import com.petfriends.transporte.valueobject.EnderecoDestino;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entregas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigoEntrega;

    @Column(nullable = false)
    private String codigoPedido;

    @Column(nullable = false)
    private String transportadora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEntrega statusEntrega;

    private LocalDateTime dataDespacho;
    private LocalDateTime dataEntrega;

    @Embedded
    private EnderecoDestino enderecoDestino;

    // ==== Regras de domínio ====
    public void despachar() {
        this.statusEntrega = StatusEntrega.EM_TRANSPORTE;
        this.dataDespacho = LocalDateTime.now();
    }

    public void marcarComoEntregue() {
        this.statusEntrega = StatusEntrega.ENTREGUE;
        this.dataEntrega = LocalDateTime.now();
    }

    public void marcarComoExtraviado() {
        this.statusEntrega = StatusEntrega.EXTRAVIADO;
    }

    public void marcarComoDevolvido() {
        this.statusEntrega = StatusEntrega.DEVOLVIDO;
    }

    public enum StatusEntrega {
        EM_TRANSPORTE,
        ENTREGUE,
        DEVOLVIDO,
        EXTRAVIADO
    }
}
