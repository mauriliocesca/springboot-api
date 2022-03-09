package br.com.api.services.interfaces;

import br.com.api.exceptions.ResourceNotFoundException;
import br.com.api.models.Contract;
import br.com.api.models.dtos.CreateContractDto;

import java.util.List;
import java.util.Optional;

public interface ContractService {

    Contract create(Contract contract);

    Contract update(Integer id, CreateContractDto createContractDto) throws ResourceNotFoundException;

    void delete(Integer id) throws ResourceNotFoundException;

    List<Contract> findAll();

    Optional<Contract> findById(Integer id);

    Optional<Contract> findByCode(String code);
}
