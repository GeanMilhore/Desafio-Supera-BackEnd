package br.com.banco.service;

import br.com.banco.controller.conta.contaDto.ContaRequest;
import br.com.banco.controller.conta.contaDto.ContaResponse;
import br.com.banco.model.Conta;
import br.com.banco.exception.BadRequestException;
import br.com.banco.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;

    public ContaResponse autenticar(ContaRequest request){

        contaRepository.findByNomeResponsavel(request.getNome_responsavel())
                .orElseThrow(() -> new BadRequestException("Nome de Usuário Incorreto!"));

        Conta conta = contaRepository.findByNomeResponsavelAndAndIdConta(request.getNome_responsavel(), request.getId_conta())
                .orElseThrow(() -> new BadRequestException("Número do Banco Incorreto!"));

        return ContaResponse.builder().id_conta(conta.getIdConta()).nome_responsavel(conta.getNomeResponsavel()).build();
    }
}
