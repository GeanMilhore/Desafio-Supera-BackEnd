package br.com.banco.repository.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ContaFilter {
    String nomeResponsavel;
    Long id;
}
