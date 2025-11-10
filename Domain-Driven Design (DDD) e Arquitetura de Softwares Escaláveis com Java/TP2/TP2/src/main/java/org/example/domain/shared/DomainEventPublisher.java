package org.example.domain.shared;

public class DomainEventPublisher {

    private static final DomainEventPublisher instance = new DomainEventPublisher();

    public static DomainEventPublisher instance() {
        return instance;
    }

    public void publish(Object event) {
        System.out.println("📢 Evento publicado: " + event);
    }
}
