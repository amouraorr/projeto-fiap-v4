package com.fiap.gestao.restaurante.mapper;


import com.fiap.gestao.restaurante.model.Address;
import com.fiap.gestao.restaurante.dto.request.AddressRequest;
import com.fiap.gestao.restaurante.dto.response.AddressResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressResponse toResponse(Address address);
    List<AddressResponse> toResponses(List<Address> users);
    Address toModel(AddressRequest request);
}


