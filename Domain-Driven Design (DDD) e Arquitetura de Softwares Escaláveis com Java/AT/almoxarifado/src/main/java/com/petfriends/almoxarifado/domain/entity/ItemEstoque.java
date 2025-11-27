package com.petfriends.almoxarifado.domain.entity;

import com.petfriends.almoxarifado.domain.valueobject.Localizacao;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "itens_estoque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigoProduto;

    @Column(nullable = false)
    private String nomeProduto;

    @Column(nullable = false)
    private Integer quantidadeDisponivel;

    @Embedded
    private Localizacao localizacao;

    public void adicionarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }
        this.quantidadeDisponivel += quantidade;
    }

    public void reduzirEstoque(int quantidade) {
        if (quantidade <= 0 || quantidade > this.quantidadeDisponivel) {
            throw new IllegalArgumentException("Quantidade insuficiente no estoque");
        }
        this.quantidadeDisponivel -= quantidade;
    }
}
