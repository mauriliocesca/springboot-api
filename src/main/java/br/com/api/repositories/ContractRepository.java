package br.com.api.repositories;

import br.com.api.models.Contract;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends CrudRepository<Contract, Integer> {

    Optional<Contract> findFirstByOrderByIdDesc();

    Optional<Contract> findByCode(String code);
}

