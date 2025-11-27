package com.petfriends.transporte.valueobject;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EnderecoDestino {

    private String rua;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;

    @Override
    public String toString() {
        return String.format("%s, %s - %s/%s, CEP: %s",
                rua, numero, cidade, estado, cep);
    }
}
