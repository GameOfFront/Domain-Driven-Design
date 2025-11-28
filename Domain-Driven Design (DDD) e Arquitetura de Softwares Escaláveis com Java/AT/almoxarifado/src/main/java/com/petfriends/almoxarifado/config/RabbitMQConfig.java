package com.petfriends.almoxarifado.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Message;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_PEDIDOS = "petfriends.pedidos.exchange";
    public static final String QUEUE_PEDIDO_CONFIRMADO = "petfriends.pedidos.pedido-confirmado";
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

    // 🚀 Conversor manual seguro — ignora headers e sempre retorna o corpo como String
    @Bean
    public MessageConverter messageConverter() {
        return new MessageConverter() {
            @Override
            public Message toMessage(Object object, MessageProperties messageProperties) {
                // Não será usado, pois esse microserviço só consome mensagens
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
