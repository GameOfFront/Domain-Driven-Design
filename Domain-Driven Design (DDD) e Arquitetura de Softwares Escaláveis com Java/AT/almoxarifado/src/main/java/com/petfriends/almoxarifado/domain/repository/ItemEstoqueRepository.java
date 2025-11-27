package com.petfriends.almoxarifado.domain.repository;

import com.petfriends.almoxarifado.domain.entity.ItemEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long> {
    Optional<ItemEstoque> findByCodigoProduto(String codigoProduto);
}
