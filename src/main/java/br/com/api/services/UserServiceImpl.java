package br.com.api.services;

import br.com.api.exceptions.ResourceNotFoundException;
import br.com.api.models.Address;
import br.com.api.models.User;
import br.com.api.models.dtos.RegisterUserDto;
import br.com.api.repositories.AddressRepository;
import br.com.api.repositories.UserRepository;
import br.com.api.services.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public User create(User user) {
        Address persistedAddress = addressRepository.save(user.getAddress());

        user.setAddress(persistedAddress);

        return userRepository.save(user);
    }

    @Override
    public User update(Integer id, RegisterUserDto registerUserDto) throws ResourceNotFoundException {
        Optional<User> optionalUser = findById(id);

        if (!optionalUser.isPresent())
            throw new ResourceNotFoundException("No user found with the Id: " + id);

        Address persistedAddress = addressRepository.save(registerUserDto.getAddress().toAddress());

        final User userToUpdate = optionalUser.get();
        userToUpdate.setName(registerUserDto.getFullName());
        userToUpdate.setEmail(registerUserDto.getEmail());
        userToUpdate.setBirthDate(registerUserDto.getDateOfBirth());
        userToUpdate.setGender(registerUserDto.getGender());
        userToUpdate.setAddress(persistedAddress);

        return userRepository.save(userToUpdate);
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException {
        Optional<User> optionalUser = findById(id);

        if (!optionalUser.isPresent())
            throw new ResourceNotFoundException("No user found with the Id: " + id);

        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return users;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }
}
