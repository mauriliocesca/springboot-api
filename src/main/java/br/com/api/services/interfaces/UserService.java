package br.com.api.services.interfaces;

import br.com.api.exceptions.ResourceNotFoundException;
import br.com.api.models.User;
import br.com.api.models.dtos.RegisterUserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user);

    User update(Integer id, RegisterUserDto registerUserDto) throws ResourceNotFoundException;

    void delete(Integer id) throws ResourceNotFoundException;

    List<User> findAll();

    Optional<User> findById(Integer id);
}
