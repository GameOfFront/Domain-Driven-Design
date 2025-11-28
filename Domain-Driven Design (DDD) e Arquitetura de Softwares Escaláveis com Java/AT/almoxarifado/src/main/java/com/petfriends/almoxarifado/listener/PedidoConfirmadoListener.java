package com.petfriends.almoxarifado.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petfriends.almoxarifado.domain.service.EstoqueService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PedidoConfirmadoListener {

    private final ObjectMapper objectMapper;
    private final EstoqueService estoqueService;

    public PedidoConfirmadoListener(EstoqueService estoqueService) {
        this.objectMapper = new ObjectMapper();
        this.estoqueService = estoqueService;
    }

    @RabbitListener(queues = "petfriends.pedidos.pedido-confirmado")
    public void receberMensagem(@Payload String mensagem) {
        try {
            if (mensagem == null || mensagem.isBlank()) {
                System.err.println("⚠️ Mensagem vazia recebida, ignorando...");
                return;
            }

            System.out.println("📨 Mensagem recebida bruta: " + mensagem);

            Map<String, Object> evento = objectMapper.readValue(mensagem, Map.class);
            System.out.println("📦 Evento convertido: " + evento);

            estoqueService.reservarItens(evento);
            System.out.println("✅ Mensagem processada com sucesso.");

        } catch (Exception e) {
            System.err.println("❌ Erro ao processar mensagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
