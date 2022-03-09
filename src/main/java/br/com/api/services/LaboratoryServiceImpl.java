package br.com.api.services;

import br.com.api.exceptions.ResourceNotFoundException;
import br.com.api.models.Laboratory;
import br.com.api.models.dtos.RegisterLaboratoryDto;
import br.com.api.repositories.LaboratoryRepository;
import br.com.api.services.interfaces.LaboratoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LaboratoryServiceImpl implements LaboratoryService {

    private final LaboratoryRepository laboratoryRepository;

    public LaboratoryServiceImpl(LaboratoryRepository laboratoryRepository) {
        this.laboratoryRepository = laboratoryRepository;
    }

    @Override
    public Laboratory create(Laboratory laboratory) {
        return laboratoryRepository.save(laboratory);
    }

    @Override
    public Laboratory update(Integer id, RegisterLaboratoryDto registerLaboratoryDto) throws ResourceNotFoundException {
        Optional<Laboratory> optionalLaboratory = findById(id);

        if (!optionalLaboratory.isPresent())
            throw new ResourceNotFoundException("No laboratory found with the Id: " + id);

        final Laboratory laboratoryToUpdate = optionalLaboratory.get();
        laboratoryToUpdate.setName(registerLaboratoryDto.getName());

        return laboratoryRepository.save(laboratoryToUpdate);
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException {
        Optional<Laboratory> optionalLaboratory = findById(id);

        if (!optionalLaboratory.isPresent())
            throw new ResourceNotFoundException("No laboratory found with the Id: " + id);

        laboratoryRepository.deleteById(id);
    }

    @Override
    public List<Laboratory> findAll() {
        List<Laboratory> laboratories = new ArrayList<>();
        laboratoryRepository.findAll().forEach(laboratories::add);

        return laboratories;
    }

    @Override
    public Optional<Laboratory> findById(Integer id) {
        return laboratoryRepository.findById(id);
    }

    public Optional<Laboratory> findByName(String name) {
        return laboratoryRepository.findByName(name);
    }
}
