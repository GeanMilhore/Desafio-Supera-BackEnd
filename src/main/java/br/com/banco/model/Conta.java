package br.com.banco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "conta")
public class Conta {

    @Id
    @Column(name = "id_conta")
    Long idConta;

    @Column(name = "nome_responsavel")
    String nomeResponsavel;

}
