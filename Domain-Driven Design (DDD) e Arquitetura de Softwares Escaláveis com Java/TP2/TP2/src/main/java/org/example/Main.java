package org.example;

import org.example.domain.pedidos.Pedido;

public class Main {
    public static void main(String[] args) {
        Pedido pedido = new Pedido(1L);
        pedido.adicionarItem(200L, 3);
        pedido.confirmar();

        System.out.println("Pedido criado: " + pedido);
    }
}
