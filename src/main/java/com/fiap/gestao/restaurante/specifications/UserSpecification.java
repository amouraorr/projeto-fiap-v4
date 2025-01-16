package com.fiap.gestao.restaurante.specifications;

import com.fiap.gestao.restaurante.enums.UserTypeEnum;
import com.fiap.gestao.restaurante.model.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class UserSpecification {

    public static Specification<User> filtros(String nome, String email, UserTypeEnum tipo) {
        return (root, query, builder) -> {
            var predicates = new ArrayList<Predicate>();

            if (nome != null && !nome.isEmpty()) {
                predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
            }
            if (email != null && !email.isEmpty()) {
                predicates.add(builder.equal(root.get("email"), email));
            }
            if (tipo != null) {
                predicates.add(builder.equal(root.get("tipo"), tipo));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
