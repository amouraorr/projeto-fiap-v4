package com.fiap.gestao.restaurante.specifications;

import com.fiap.gestao.restaurante.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserSpecificationTest {

    @Mock
    private Root<User> root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder builder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar predicado apenas para nome")
    void shouldReturnPredicateForNameOnly() {
        String nome = "John";
        String email = null;

        Predicate nomePredicate = mock(Predicate.class);

        when(builder.like(root.get("nome"), "%John%")).thenReturn(nomePredicate);
        when(builder.and(nomePredicate)).thenReturn(mock(Predicate.class));

        Specification<User> spec = UserSpecification.filtros(nome, email);
        Predicate predicate = spec.toPredicate(root, query, builder);

        assertNotNull(predicate);
        verify(builder).like(root.get("nome"), "%John%");
        verify(builder, never()).equal(root.get("email"), email);
    }

    @Test
    @DisplayName("Deve retornar predicado apenas para email")
    void shouldReturnPredicateForEmailOnly() {
        String nome = null;
        String email = "john.doe@example.com";

        Predicate emailPredicate = mock(Predicate.class);

        when(builder.equal(root.get("email"), email)).thenReturn(emailPredicate);
        when(builder.and(emailPredicate)).thenReturn(mock(Predicate.class));

        Specification<User> spec = UserSpecification.filtros(nome, email);
        Predicate predicate = spec.toPredicate(root, query, builder);

        assertNotNull(predicate);
        verify(builder, never()).like(root.get("nome"), "%John%");
        verify(builder).equal(root.get("email"), email);
    }

    @Test
    @DisplayName("Deve retornar predicado vazio quando nenhum filtro é fornecido")
    void shouldReturnEmptyPredicateWhenNoFilterProvided() {
        String nome = null;
        String email = null;

        when(builder.conjunction()).thenReturn(mock(Predicate.class));

        Specification<User> spec = UserSpecification.filtros(nome, email);
        Predicate predicate = spec.toPredicate(root, query, builder);

        assertNotNull(predicate);
        verify(builder, never()).like(any(), anyString());
        verify(builder, never()).equal(any(), any());
    }
}