package br.com.banco.controller.transferencias.transferenciaDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaResponse {
    public Long id;
    public LocalDate data_transferecia;
    public Double valor;
    public String tipo;
    public String nomeOperador;
    public Long conta_id;
}
