package br.com.banco.mapper;

import br.com.banco.controller.conta.contaDto.ContaResponse;
import br.com.banco.domain.Conta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ContaMapper {
    public static final ContaMapper INSTANCE = Mappers.getMapper(ContaMapper.class);

    public abstract ContaResponse toResponse(Conta conta);
}
