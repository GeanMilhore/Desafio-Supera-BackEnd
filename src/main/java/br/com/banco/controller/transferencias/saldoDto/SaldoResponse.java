package br.com.banco.controller.transferencias.saldoDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SaldoResponse {
    public Double saldoTotal;
    public Double saldoPeriodo;
}
