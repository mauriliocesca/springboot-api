package br.com.api.services.interfaces;

import br.com.api.exceptions.ResourceNotFoundException;
import br.com.api.models.Laboratory;
import br.com.api.models.dtos.RegisterLaboratoryDto;

import java.util.List;
import java.util.Optional;

public interface LaboratoryService {

    Laboratory create(Laboratory laboratory);

    Laboratory update(Integer id, RegisterLaboratoryDto registerLaboratoryDto) throws ResourceNotFoundException;

    void delete(Integer id) throws ResourceNotFoundException;

    List<Laboratory> findAll();

    Optional<Laboratory> findById(Integer id);

    Optional<Laboratory> findByName(String name);
}
