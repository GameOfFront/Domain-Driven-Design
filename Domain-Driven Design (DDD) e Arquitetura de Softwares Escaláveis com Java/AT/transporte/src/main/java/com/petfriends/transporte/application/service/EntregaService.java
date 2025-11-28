package com.petfriends.transporte.application.service;

import com.petfriends.transporte.domain.entity.Entrega;
import com.petfriends.transporte.domain.repository.EntregaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class EntregaService {

    private final EntregaRepository repository;

    public EntregaService(EntregaRepository repository) {
        this.repository = repository;
    }

    // ======= CRUD EXISTENTE =======
    public List<Entrega> listarTodas() {
        return repository.findAll();
    }

    public Entrega buscarPorCodigoEntrega(String codigoEntrega) {
        return repository.findByCodigoEntrega(codigoEntrega)
                .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));
    }

    public Entrega salvar(Entrega entrega) {
        return repository.save(entrega);
    }

    public Entrega atualizarStatus(String codigoEntrega, Entrega.StatusEntrega novoStatus) {
        Entrega entrega = buscarPorCodigoEntrega(codigoEntrega);
        entrega.setStatusEntrega(novoStatus);
        return repository.save(entrega);
    }

    // ======= NOVO MÉTODO PARA EVENTOS DO RABBITMQ =======
    public void criarEntregaApartirDoPedido(Map<String, Object> evento) {
        try {
            String codigoPedido = (String) evento.get("pedidoId");
            String clienteId = (String) evento.get("clienteId");

            String codigoEntrega = "ENT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            Entrega entrega = Entrega.builder()
                    .codigoEntrega(codigoEntrega)
                    .codigoPedido(codigoPedido)
                    .transportadora("PetFriends Express")
                    .statusEntrega(Entrega.StatusEntrega.EM_TRANSPORTE)
                    .dataDespacho(LocalDateTime.now())
                    .build();

            repository.save(entrega);

            System.out.printf("🚚 Nova entrega criada para o pedido %s (cliente %s) → código: %s%n",
                    codigoPedido, clienteId, codigoEntrega);

        } catch (Exception e) {
            System.err.println("❌ Erro ao criar entrega a partir do evento: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
