package br.com.api.controllers;

import br.com.api.exceptions.ResourceNotFoundException;
import br.com.api.models.Property;
import br.com.api.models.dtos.RegisterPropertyDto;
import br.com.api.services.interfaces.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/property")
@RestController
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping("/register")
    public ResponseEntity<Property> registerProperty(@Valid @RequestBody RegisterPropertyDto registerPropertyDto) {
        Property createdProperty = propertyService.create(registerPropertyDto.toProperty());

        return new ResponseEntity<>(createdProperty, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable @NotBlank Integer id, @Valid @RequestBody RegisterPropertyDto registerPropertyDto) throws ResourceNotFoundException {
        Property updatedProperty = propertyService.update(id, registerPropertyDto);

        return new ResponseEntity<>(updatedProperty, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable @NotBlank Integer id) throws ResourceNotFoundException {
        propertyService.delete(id);

        return new ResponseEntity<>("Property: " + id + " deleted.", HttpStatus.OK);
    }

    @GetMapping("/{pin}")
    public ResponseEntity<Property> getUserById(@PathVariable @NotBlank Integer pin) throws ResourceNotFoundException {
        Optional<Property> optionalProperty = propertyService.findByPin(pin);

        if (!optionalProperty.isPresent())
            throw new ResourceNotFoundException("No property found with the Pin: " + pin);

        return new ResponseEntity<>(optionalProperty.get(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Property>> allProperties() {
        return new ResponseEntity<>(propertyService.findAll(), HttpStatus.OK);
    }
}
