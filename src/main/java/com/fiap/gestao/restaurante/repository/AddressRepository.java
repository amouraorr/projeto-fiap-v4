package com.fiap.gestao.restaurante.repository;

import com.fiap.gestao.restaurante.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}