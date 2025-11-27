package com.petfriends.almoxarifado.domain.valueobject;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Localizacao {

    private String corredor;
    private String prateleira;
    private String nivel;

    @Override
    public String toString() {
        return String.format("Corredor: %s | Prateleira: %s | Nível: %s",
                corredor, prateleira, nivel);
    }
}
