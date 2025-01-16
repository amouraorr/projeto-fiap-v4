package com.fiap.gestao.restaurante.repository;

import com.fiap.gestao.restaurante.model.Address;
import com.fiap.gestao.restaurante.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
