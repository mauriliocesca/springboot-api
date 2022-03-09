package br.com.api.controllers;

import br.com.api.exceptions.ResourceNotFoundException;
import br.com.api.models.User;
import br.com.api.models.dtos.RegisterUserDto;
import br.com.api.services.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/user")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User createdUser = userService.create(registerUserDto.toUser());

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable @NotBlank Integer id, @Valid @RequestBody RegisterUserDto registerUserDto) throws ResourceNotFoundException {
        User updatedUser = userService.update(id, registerUserDto);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable @NotBlank Integer id) throws ResourceNotFoundException {
        userService.delete(id);

        return new ResponseEntity<>("User: " + id + " deleted.", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable @NotBlank Integer id) throws ResourceNotFoundException {
        Optional<User> optionalUser = userService.findById(id);

        if (!optionalUser.isPresent())
            throw new ResourceNotFoundException("No user found with the Id: " + id);

        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> allUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
}
