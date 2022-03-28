package br.com.banco.unitTest;

import br.com.banco.controller.transferencias.saldoDto.SaldoResponse;
import br.com.banco.exception.BadRequestException;
import br.com.banco.model.Transferencia;
import br.com.banco.repository.TransferenciaRepository;
import br.com.banco.repository.filter.TransferenciaFilter;
import br.com.banco.service.TransferenciaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;

public class TransferenciaServiceTest {

    private TransferenciaService transferenciaService;

    @Mock
    TransferenciaRepository transferenciaRepositoryMock;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.initMocks(this);
        this.transferenciaService = new TransferenciaService(transferenciaRepositoryMock);
    }
    @Test
    void buscaTransferenciasQuandoSucesso(){
        TransferenciaFilter filter = criarFilter();

        Pageable pageable = Pageable.ofSize(4).withPage(1);

        Mockito.when(transferenciaRepositoryMock
                .findAll(ArgumentMatchers.any(Specification.class),ArgumentMatchers.any(Pageable.class)))
                .thenReturn(simularLista());

        transferenciaService.buscarTransferencias(pageable, filter);

        Mockito.verify(transferenciaRepositoryMock, Mockito.times(1))
                .findAll(ArgumentMatchers.any(Specification.class), ArgumentMatchers.any(Pageable.class));
    }

    @Test
    void somaValoresDaListaQuandoSucesso(){
        List<Transferencia> listaValores = simularListaValores();

        Double valor = transferenciaService.somarValoresLista(listaValores);

        Assertions.assertEquals(valor, 6150d);
    }

    @Test
    void calculaSaldosQuandoSucesso(){
        TransferenciaFilter filter = criarFilter();

        Mockito.when(transferenciaRepositoryMock
                        .findAllByContaId(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(simularListaValores()));


        Mockito.when(transferenciaRepositoryMock
                        .findAll(ArgumentMatchers.any(Specification.class)))
                .thenReturn(simularListaPeriodoMenor());

        SaldoResponse saldo = transferenciaService.buscarSaldos(filter);

        Mockito.verify(transferenciaRepositoryMock, Mockito.times(1))
                .findAll(ArgumentMatchers.any(Specification.class));

        Mockito.verify(transferenciaRepositoryMock, Mockito.times(1))
                .findAllByContaId(ArgumentMatchers.anyLong());

        Assertions.assertEquals(saldo.getSaldoPeriodo(), 2050d);
        Assertions.assertEquals(saldo.getSaldoTotal(), 6150d);
    }

    @Test
    void buscaSaldoPeloIdLancaExcecaoQuandoSucesso(){
        TransferenciaFilter filter = criarFilter();

        Mockito.when(transferenciaRepositoryMock
                        .findAllByContaId(ArgumentMatchers.anyLong()))
                        .thenThrow(new BadRequestException("Nenhuma Transferência nesta conta"));

        try{
            transferenciaService.buscarSaldos(filter);
            fail();
        } catch (Exception e){
            Assertions.assertEquals(e.getClass(), BadRequestException.class);
            Assertions.assertEquals(e.getMessage(), "Nenhuma Transferência nesta conta");
            Mockito.verify(transferenciaRepositoryMock, Mockito.times(0)).findAll(ArgumentMatchers.any(Specification.class));
        }
    }

    public TransferenciaFilter criarFilter(){
        return new TransferenciaFilter(
                2l,
                "2022-01-01",
                "2022-02-02",
                "Fulano");
    }

    public Page<Transferencia> simularLista(){
        PageRequest paginacao = PageRequest.of(1, 10);
        Transferencia transferencia = new Transferencia();
        transferencia.setData_transferencia(LocalDate.now());
        transferencia.setContaId(2l);
        transferencia.setValor(2050.00d);
        transferencia.setTipo("TRANSFERENCIA");
        transferencia.setId(30l);
        transferencia.setNomeOperador("Jusbiscleys");

        List<Transferencia> transferencias = Arrays.asList(transferencia, transferencia, transferencia);
        Page<Transferencia> transferenciasPage = new PageImpl<>(transferencias, paginacao, 4);

        return transferenciasPage;
    }

    public List<Transferencia> simularListaValores(){
        Transferencia transferencia = new Transferencia();
        transferencia.setData_transferencia(LocalDate.now());
        transferencia.setContaId(2l);
        transferencia.setValor(2050.00d);
        transferencia.setTipo("TRANSFERENCIA");
        transferencia.setId(30l);
        transferencia.setNomeOperador("Jusbiscleys");

        List<Transferencia> transferencias = new ArrayList<Transferencia>();
        transferencias.add(transferencia);
        transferencias.add(transferencia);
        transferencias.add(transferencia);

        return transferencias;
    }

    public List<Transferencia> simularListaPeriodoMenor(){
        Transferencia transferencia = new Transferencia();
        transferencia.setData_transferencia(LocalDate.now());
        transferencia.setContaId(2l);
        transferencia.setValor(2050.00d);
        transferencia.setTipo("TRANSFERENCIA");
        transferencia.setId(30l);
        transferencia.setNomeOperador("Jusbiscleys");

        List<Transferencia> transferencias = new ArrayList<Transferencia>();
        transferencias.add(transferencia);

        return transferencias;
    }
}