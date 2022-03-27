package br.com.banco.repository.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TransferenciaFilter {

        Long idConta;
        @JsonFormat(pattern = "yyyy-MM-dd")
        String dataInicio;
        @JsonFormat(pattern = "yyyy-MM-dd")
        String dataFim;
        String nomeOperador;
}
