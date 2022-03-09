package br.com.api.services.interfaces;

import br.com.api.exceptions.ResourceNotFoundException;
import br.com.api.models.Property;
import br.com.api.models.dtos.RegisterPropertyDto;

import java.util.List;
import java.util.Optional;

public interface PropertyService {

    Property create(Property property);

    Property update(Integer id, RegisterPropertyDto registerPropertyDto) throws ResourceNotFoundException;

    void delete(Integer id) throws ResourceNotFoundException;

    List<Property> findAll();

    Optional<Property> findById(Integer id);

    Optional<Property> findByPin(Integer pin);
}
