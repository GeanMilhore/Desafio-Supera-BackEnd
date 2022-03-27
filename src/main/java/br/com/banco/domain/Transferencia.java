package br.com.banco.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@Data
public class Transferencia {
    @Id
    Long id;

    @Column(name = "data_transferencia")
    LocalDate data_transferencia;

    @Column(name = "valor")
    Double valor;

    @Column(name = "tipo")
    String tipo;

    @Column(name = "nome_operador_transacao")
    String nomeOperador;

    @Column(name = "conta_id")
    Long contaId;
}
