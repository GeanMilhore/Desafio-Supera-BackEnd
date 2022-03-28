package br.com.banco.unitTest;

import br.com.banco.controller.conta.contaDto.ContaRequest;
import br.com.banco.controller.conta.contaDto.ContaResponse;
import br.com.banco.exception.BadRequestException;
import br.com.banco.model.Conta;
import br.com.banco.repository.ContaRepository;
import br.com.banco.service.ContaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;

public class ContaServiceTest {

    private ContaService contaService;

    @Mock
    private ContaRepository contaRepositoryMock;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.initMocks(this);
        this.contaService = new ContaService(contaRepositoryMock);
    }

    @Test
    void authenticaUsuarioQuandoSucesso() {
        ContaRequest request = criarContaRequest();

        Mockito.when(contaRepositoryMock.findByNomeResponsavel(Mockito.anyString()))
                .thenReturn(Optional.of(criarConta()));

        Mockito.when(contaRepositoryMock.findByNomeResponsavelAndAndIdConta(Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(Optional.of(criarConta()));

        ContaResponse response = contaService.autenticar(request);

        Mockito.verify(contaRepositoryMock, Mockito.times(1)).findByNomeResponsavel(Mockito.anyString());
        Mockito.verify(contaRepositoryMock, Mockito.times(1))
                .findByNomeResponsavelAndAndIdConta(Mockito.anyString(), Mockito.anyLong());

        Assertions.assertEquals(request.getNome_responsavel(), response.getNome_responsavel());
        Assertions.assertEquals(request.getId_conta(), response.getId_conta());
    }

    @Test
    void autenticarLancaExcecaoNomeIncorretoQuandoSucesso() {
        ContaRequest request = criarContaRequest();

        Mockito.when(contaRepositoryMock.findByNomeResponsavel(Mockito.anyString()))
                .thenThrow(new BadRequestException("Nome de Usuário Incorreto!"));

        try {
            contaService.autenticar(request);
            fail();
        } catch (Exception e) {
            Mockito.verify(contaRepositoryMock, Mockito.times(1)).findByNomeResponsavel(Mockito.anyString());
            Mockito.verify(contaRepositoryMock, Mockito.times(0))
                    .findByNomeResponsavelAndAndIdConta(Mockito.anyString(), Mockito.anyLong());

            Assertions.assertEquals(e.getMessage(), "Nome de Usuário Incorreto!");
            Assertions.assertEquals(e.getClass(), BadRequestException.class);
        }

    }

    @Test
    void autenticarLancaExcecaoIdIncorretoQuandoSucesso() {
        ContaRequest request = criarContaRequest();

        Mockito.when(contaRepositoryMock.findByNomeResponsavel(Mockito.anyString()))
                .thenReturn(Optional.of(criarConta()));

        Mockito.when(contaRepositoryMock.findByNomeResponsavelAndAndIdConta(Mockito.anyString(), Mockito.anyLong()))
                .thenThrow(new BadRequestException("Número do Banco Incorreto!"));

        try {
            ContaResponse contaResponse = contaService.autenticar(request);
            fail();
        } catch (Exception e) {
            Mockito.verify(contaRepositoryMock, Mockito.times(1)).findByNomeResponsavel(Mockito.anyString());
            Mockito.verify(contaRepositoryMock, Mockito.times(1))
                    .findByNomeResponsavelAndAndIdConta(Mockito.anyString(), Mockito.anyLong());

            Assertions.assertEquals(e.getMessage(), "Número do Banco Incorreto!");
            Assertions.assertEquals(e.getClass(), BadRequestException.class);
        }

    }

    public ContaRequest criarContaRequest() {
        return new ContaRequest()
                .builder()
                .id_conta(1l)
                .nome_responsavel("Fulano")
                .build();
    }

    public Conta criarConta() {
        Conta conta = new Conta();
        conta.setIdConta(1l);
        conta.setNomeResponsavel("Fulano");
        return conta;
    }
}
