package com.petfriends.almoxarifado.infra.web;

import com.petfriends.almoxarifado.application.service.ItemEstoqueService;
import com.petfriends.almoxarifado.domain.entity.ItemEstoque;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class ItemEstoqueController {

    private final ItemEstoqueService service;

    public ItemEstoqueController(ItemEstoqueService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ItemEstoque>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{codigoProduto}")
    public ResponseEntity<ItemEstoque> buscarPorCodigo(@PathVariable String codigoProduto) {
        return ResponseEntity.ok(service.buscarPorCodigoProduto(codigoProduto));
    }

    @PostMapping
    public ResponseEntity<ItemEstoque> criarItem(@RequestBody ItemEstoque item) {
        return ResponseEntity.ok(service.salvar(item));
    }

    @PutMapping("/{codigoProduto}/adicionar/{quantidade}")
    public ResponseEntity<Void> adicionar(@PathVariable String codigoProduto, @PathVariable int quantidade) {
        service.adicionarEstoque(codigoProduto, quantidade);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{codigoProduto}/reduzir/{quantidade}")
    public ResponseEntity<Void> reduzir(@PathVariable String codigoProduto, @PathVariable int quantidade) {
        service.reduzirEstoque(codigoProduto, quantidade);
        return ResponseEntity.noContent().build();
    }
}
