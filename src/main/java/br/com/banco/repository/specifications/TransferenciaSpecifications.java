package br.com.banco.repository.specifications;

import br.com.banco.model.Transferencia;
import br.com.banco.repository.filter.TransferenciaFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
public class TransferenciaSpecifications implements Specification<Transferencia> {

    private final TransferenciaFilter filtro;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void verificarIdConta(Predicate predicate, CriteriaBuilder cb, Root<Transferencia> root) {
        Optional.ofNullable(filtro)
                .map(TransferenciaFilter::getIdConta)
                .ifPresent(idConta -> {
                    predicate.getExpressions().add(cb.equal(root.get("contaId"), idConta));
                });
    }

    public void verificaDataInicio(Predicate predicate, CriteriaBuilder cb, Root<Transferencia> root) {
        Optional.ofNullable(filtro)
                .map(TransferenciaFilter::getDataInicio)
                .ifPresent(dataInicio -> {
                    predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("data_transferencia"),
                            LocalDate.parse(dataInicio, formatter)));
                });
    }

    public void verificaDataFim(Predicate predicate, CriteriaBuilder cb, Root<Transferencia> root) {
        Optional.ofNullable(filtro)
                .map(TransferenciaFilter::getDataFim)
                .ifPresent(dataFim -> {
                    predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("data_transferencia"),
                            LocalDate.parse(dataFim, formatter).plusDays(1)));
                });
    }


    public void verificaNomeOperador(Predicate predicate, CriteriaBuilder cb, Root<Transferencia> root) {
        Optional.ofNullable(filtro)
                .map(TransferenciaFilter::getNomeOperador)
                .ifPresent(nomeOperador -> {
                    predicate.getExpressions()
                            .add(cb.like(cb.upper(root
                                    .get("nomeOperador")), "%" + nomeOperador.toUpperCase(Locale.ROOT) + "%"));
                });
    }




    @Override
    public Predicate toPredicate(Root<Transferencia> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        verificarIdConta(predicate, criteriaBuilder, root);
        verificaDataInicio(predicate, criteriaBuilder, root);
        verificaDataFim(predicate, criteriaBuilder, root);
        verificaNomeOperador(predicate, criteriaBuilder, root);

        return predicate;
    }
}