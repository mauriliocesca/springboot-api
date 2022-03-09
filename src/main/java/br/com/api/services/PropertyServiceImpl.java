package br.com.api.services;

import br.com.api.exceptions.ResourceNotFoundException;
import br.com.api.models.Property;
import br.com.api.models.dtos.RegisterPropertyDto;
import br.com.api.repositories.PropertyRepository;
import br.com.api.services.interfaces.PropertyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Property create(Property property) {
        Optional<Property> optionalProperty = findByPin(property.getPin());

        if (optionalProperty.isPresent())
            throw new IllegalArgumentException("The field Pin must be unique.");

        return propertyRepository.save(property);
    }

    @Override
    public Property update(Integer id, RegisterPropertyDto registerPropertyDto) throws ResourceNotFoundException {
        Optional<Property> optionalPropertyPinExists = findByPin(registerPropertyDto.getPin());

        if (optionalPropertyPinExists.isPresent())
            throw new IllegalArgumentException("The field Pin must be unique.");

        Optional<Property> optionalProperty = findById(id);

        if (!optionalProperty.isPresent())
            throw new ResourceNotFoundException("No property found with the Id: " + id);

        final Property propertyToUpdate = optionalProperty.get();
        propertyToUpdate.setName(registerPropertyDto.getName());
        propertyToUpdate.setPin(registerPropertyDto.getPin());

        return propertyRepository.save(propertyToUpdate);
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException {
        Optional<Property> optionalProperty = findById(id);

        if (!optionalProperty.isPresent())
            throw new ResourceNotFoundException("No property found with the Id: " + id);

        propertyRepository.deleteById(id);
    }

    @Override
    public List<Property> findAll() {
        List<Property> properties = new ArrayList<>();
        propertyRepository.findAll().forEach(properties::add);

        return properties;
    }

    @Override
    public Optional<Property> findById(Integer id) {
        return propertyRepository.findById(id);
    }

    @Override
    public Optional<Property> findByPin(Integer pin) {
        return propertyRepository.findByPin(pin);
    }
}
