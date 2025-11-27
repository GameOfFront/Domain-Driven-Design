package com.petfriends.transporte.application.service;

import com.petfriends.transporte.domain.entity.Entrega;
import com.petfriends.transporte.domain.repository.EntregaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EntregaService {

    private final EntregaRepository repository;

    public EntregaService(EntregaRepository repository) {
        this.repository = repository;
    }

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
}
