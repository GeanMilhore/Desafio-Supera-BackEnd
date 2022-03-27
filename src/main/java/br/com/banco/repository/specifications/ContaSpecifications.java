package br.com.banco.repository.specifications;

import br.com.banco.domain.Conta;
import br.com.banco.repository.filter.ContaFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequiredArgsConstructor
public class ContaSpecifications implements Specification<Conta> {

    private final ContaFilter filtro;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void verificarNumeroBanco(Predicate predicate, CriteriaBuilder cb, Root<Conta> root) {
        Optional.ofNullable(filtro)
                .map(ContaFilter::getId)
                .ifPresent(id -> {
                    predicate.getExpressions().add(cb.equal(root.get("id_conta"), id));
                });
    }

    public void verificarUsuario(Predicate predicate, CriteriaBuilder cb, Root<Conta> root) {
        Optional.ofNullable(filtro)
                .map(ContaFilter::getId)
                .ifPresent(nome -> {
                    predicate.getExpressions().add(cb.equal(root.get("nome_responsavel"), nome));
                });
    }


    @Override
    public Predicate toPredicate(Root<Conta> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        verificarNumeroBanco(predicate, criteriaBuilder, root);
        verificarUsuario(predicate, criteriaBuilder, root);

        return predicate;
    }
}