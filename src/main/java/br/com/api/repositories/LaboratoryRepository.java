package br.com.api.repositories;

import br.com.api.models.Laboratory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LaboratoryRepository extends CrudRepository<Laboratory, Integer> {

    Optional<Laboratory> findByName(String name);
}
