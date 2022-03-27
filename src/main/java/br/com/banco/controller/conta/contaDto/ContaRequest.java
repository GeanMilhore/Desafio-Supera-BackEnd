package br.com.banco.controller.conta.contaDto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContaRequest {
    public Long id_conta;
    public String nome_responsavel;
}
