package com.petfriends.almoxarifado.application.service;

import com.petfriends.almoxarifado.domain.entity.ItemEstoque;
import com.petfriends.almoxarifado.domain.repository.ItemEstoqueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemEstoqueService {

    private final ItemEstoqueRepository repository;

    public ItemEstoqueService(ItemEstoqueRepository repository) {
        this.repository = repository;
    }

    public List<ItemEstoque> listarTodos() {
        return repository.findAll();
    }

    public ItemEstoque buscarPorCodigoProduto(String codigoProduto) {
        return repository.findByCodigoProduto(codigoProduto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado no estoque"));
    }

    public ItemEstoque salvar(ItemEstoque item) {
        return repository.save(item);
    }

    public void adicionarEstoque(String codigoProduto, int quantidade) {
        ItemEstoque item = buscarPorCodigoProduto(codigoProduto);
        item.adicionarEstoque(quantidade);
        repository.save(item);
    }

    public void reduzirEstoque(String codigoProduto, int quantidade) {
        ItemEstoque item = buscarPorCodigoProduto(codigoProduto);
        item.reduzirEstoque(quantidade);
        repository.save(item);
    }
}
