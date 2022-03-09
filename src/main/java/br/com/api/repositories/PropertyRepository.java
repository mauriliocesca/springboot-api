package br.com.api.repositories;

import br.com.api.models.Property;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyRepository extends CrudRepository<Property, Integer> {

    Optional<Property> findByPin(Integer pin);
}
