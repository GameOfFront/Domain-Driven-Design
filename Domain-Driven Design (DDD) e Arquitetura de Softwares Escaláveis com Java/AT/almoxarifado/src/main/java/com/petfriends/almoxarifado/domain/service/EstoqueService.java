package com.petfriends.almoxarifado.domain.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petfriends.almoxarifado.domain.repository.ItemEstoqueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Serviço responsável por processar os eventos recebidos do microsserviço PetFriends_Pedidos
 * e aplicar as regras de negócio relacionadas à atualização de estoque.
 */
@Service
@Transactional
public class EstoqueService {

    private final ItemEstoqueRepository itemEstoqueRepository;
    private final ObjectMapper objectMapper;

    public EstoqueService(ItemEstoqueRepository itemEstoqueRepository) {
        this.itemEstoqueRepository = itemEstoqueRepository;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Processa o evento PedidoConfirmadoEvent recebido via RabbitMQ.
     * Reduz as quantidades dos produtos conforme os itens do pedido.
     *
     * @param evento mapa representando o evento recebido do RabbitMQ
     */
    public void reservarItens(Map<String, Object> evento) {
        try {
            List<Map<String, Object>> itens = objectMapper.convertValue(
                    evento.get("itens"),
                    new TypeReference<List<Map<String, Object>>>() {}
            );

            for (Map<String, Object> item : itens) {
                String produtoId = (String) item.get("produtoId");
                Integer quantidade = (Integer) item.get("quantidade");

                itemEstoqueRepository.findByCodigoProduto(produtoId)
                        .ifPresentOrElse(itemEstoque -> {
                            try {
                                itemEstoque.reduzirEstoque(quantidade);
                                itemEstoqueRepository.save(itemEstoque);
                                System.out.printf("✅ Estoque atualizado: %s - quantidade reduzida: %d%n",
                                        produtoId, quantidade);
                            } catch (IllegalArgumentException e) {
                                System.err.printf("⚠️ Falha ao reduzir estoque do produto %s: %s%n",
                                        produtoId, e.getMessage());
                            }
                        }, () -> System.err.printf("🚫 Produto %s não encontrado no estoque!%n", produtoId));
            }

        } catch (Exception e) {
            System.err.println("❌ Erro ao processar evento de reserva de itens: " + e.getMessage());
        }
    }
}
