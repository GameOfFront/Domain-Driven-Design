package org.example.domain.pedidos;

public class ItemPedido {
    private final Long produtoId;
    private final int quantidade;

    public ItemPedido(Long produtoId, int quantidade) {
        if (quantidade <= 0)
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public Long getProdutoId() { return produtoId; }
    public int getQuantidade() { return quantidade; }
}
