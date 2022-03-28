package br.com.banco.service;

import br.com.banco.controller.transferencias.saldoDto.SaldoResponse;
import br.com.banco.model.Transferencia;
import br.com.banco.exception.BadRequestException;
import br.com.banco.repository.TransferenciaRepository;
import br.com.banco.repository.filter.TransferenciaFilter;
import br.com.banco.repository.specifications.TransferenciaSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransferenciaService {

    private TransferenciaRepository transferenciaRepository;

    public Page<Transferencia> buscarTransferencias(Pageable pageable, TransferenciaFilter filter){
        return transferenciaRepository.findAll(new TransferenciaSpecifications(filter), pageable);
    }

    public SaldoResponse buscarSaldos(TransferenciaFilter filter) {
        List<Transferencia> listaTransferencias = transferenciaRepository.findAllByContaId(filter.getIdConta())
                .orElseThrow(() -> new BadRequestException("Nenhuma Transferência nesta conta"));

        Double saldoTotal = somarValoresLista(listaTransferencias);

        List<Transferencia> listaFiltrada = transferenciaRepository.findAll(new TransferenciaSpecifications(filter));
        Double saldoPeriodo = somarValoresLista(listaFiltrada);

        SaldoResponse saldo  = SaldoResponse
                                            .builder()
                                            .saldoTotal(saldoTotal)
                                            .saldoPeriodo(saldoPeriodo)
                                            .build();
        return saldo;
    }

    public Double somarValoresLista(List<Transferencia> lista){
        List<Double> valores = lista.stream().map(Transferencia::getValor).collect(Collectors.toList());
        Double totalSoma = valores.stream().reduce(0d, (subtotal, element) -> subtotal + element);
        return totalSoma;
    }
}
