package com.petfriends.transporte.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.core.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_PEDIDOS = "petfriends.pedidos.exchange";
    public static final String QUEUE_PEDIDO_CONFIRMADO = "petfriends.transporte.pedido-confirmado";
    public static final String ROUTING_KEY_PEDIDO_CONFIRMADO = "pedido.confirmado";

    @Bean
    public TopicExchange pedidosExchange() {
        return new TopicExchange(EXCHANGE_PEDIDOS);
    }

    @Bean
    public Queue pedidoConfirmadoQueue() {
        return new Queue(QUEUE_PEDIDO_CONFIRMADO, true);
    }

    @Bean
    public Binding pedidoConfirmadoBinding(Queue pedidoConfirmadoQueue, TopicExchange pedidosExchange) {
        return BindingBuilder
                .bind(pedidoConfirmadoQueue)
                .to(pedidosExchange)
                .with(ROUTING_KEY_PEDIDO_CONFIRMADO);
    }

    // 🚀 Conversor simples e seguro — ignora headers e lê apenas o corpo da mensagem
    @Bean
    public MessageConverter messageConverter() {
        return new MessageConverter() {
            @Override
            public Message toMessage(Object object, MessageProperties messageProperties) {
                // Não usado (apenas consumo)
                return null;
            }

            @Override
            public Object fromMessage(Message message) {
                try {
                    return new String(message.getBody());
                } catch (Exception e) {
                    System.err.println("⚠️ Erro ao converter mensagem: " + e.getMessage());
                    return null;
                }
            }
        };
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
