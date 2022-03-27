package br.com.banco.mapper;

import br.com.banco.controller.transferencias.transferenciaDto.TransferenciaResponse;
import br.com.banco.domain.Transferencia;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class TransferenciaMapper {
    public static final TransferenciaMapper INSTANCE = Mappers.getMapper(TransferenciaMapper.class);

    public abstract TransferenciaResponse toTransferenciaResponse(Transferencia transferencia);
}
