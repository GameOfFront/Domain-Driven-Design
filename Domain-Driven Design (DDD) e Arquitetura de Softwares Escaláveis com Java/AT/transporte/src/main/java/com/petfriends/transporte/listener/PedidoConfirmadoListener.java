package com.petfriends.transporte.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petfriends.transporte.application.service.EntregaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Listener responsável por escutar os eventos do RabbitMQ
 * e acionar o serviço de transporte para criar entregas.
 */
@Component
public class PedidoConfirmadoListener {

    private final ObjectMapper objectMapper;
    private final EntregaService entregaService;

    public PedidoConfirmadoListener(EntregaService entregaService) {
        this.objectMapper = new ObjectMapper();
        this.entregaService = entregaService;
    }

    @RabbitListener(queues = "petfriends.transporte.pedido-confirmado")
    public void receberMensagem(String mensagem) {
        try {
            System.out.println("📨 [Transporte] Mensagem recebida: " + mensagem);

            Map<String, Object> evento = objectMapper.readValue(mensagem, Map.class);
            System.out.println("📦 [Transporte] Evento convertido: " + evento);

            entregaService.criarEntregaApartirDoPedido(evento);
            System.out.println("✅ [Transporte] Entrega criada com sucesso.");

        } catch (Exception e) {
            System.err.println("❌ [Transporte] Erro ao processar mensagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
