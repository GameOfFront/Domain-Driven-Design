package com.petfriends.transporte.web;

import com.petfriends.transporte.application.service.EntregaService;
import com.petfriends.transporte.domain.entity.Entrega;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entregas")
public class EntregaController {

    private final EntregaService service;

    public EntregaController(EntregaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Entrega>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{codigoEntrega}")
    public ResponseEntity<Entrega> buscarPorCodigo(@PathVariable String codigoEntrega) {
        return ResponseEntity.ok(service.buscarPorCodigoEntrega(codigoEntrega));
    }

    @PostMapping
    public ResponseEntity<Entrega> criar(@RequestBody Entrega entrega) {
        return ResponseEntity.ok(service.salvar(entrega));
    }

    @PutMapping("/{codigoEntrega}/status/{novoStatus}")
    public ResponseEntity<Entrega> atualizarStatus(@PathVariable String codigoEntrega,
                                                   @PathVariable Entrega.StatusEntrega novoStatus) {
        return ResponseEntity.ok(service.atualizarStatus(codigoEntrega, novoStatus));
    }
}
