package br.com.api.controllers;

import br.com.api.exceptions.ResourceNotFoundException;
import br.com.api.models.Laboratory;
import br.com.api.models.dtos.RegisterLaboratoryDto;
import br.com.api.services.interfaces.LaboratoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RequestMapping(value = "/laboratory")
@RestController
public class LaboratoryController {

    private final LaboratoryService laboratoryService;

    public LaboratoryController(LaboratoryService laboratoryService) {
        this.laboratoryService = laboratoryService;
    }

    @PostMapping("/register")
    public ResponseEntity<Laboratory> registerLaboratory(@Valid @RequestBody RegisterLaboratoryDto registerLaboratoryDto) {
        Laboratory createdLaboratory = laboratoryService.create(registerLaboratoryDto.toLaboratory());

        return new ResponseEntity<>(createdLaboratory, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Laboratory> updateLaboratory(@PathVariable @NotBlank Integer id, @Valid @RequestBody RegisterLaboratoryDto registerLaboratoryDto) throws ResourceNotFoundException {
        Laboratory updatedLaboratory = laboratoryService.update(id, registerLaboratoryDto);

        return new ResponseEntity<>(updatedLaboratory, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable @NotBlank Integer id) throws ResourceNotFoundException {
        laboratoryService.delete(id);

        return new ResponseEntity<>("Laboratory: " + id + " deleted.", HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Laboratory> getLaboratoryByName(@PathVariable @NotBlank String name) throws ResourceNotFoundException {
        Optional<Laboratory> optionalLaboratory = laboratoryService.findByName(name);

        if (!optionalLaboratory.isPresent())
            throw new ResourceNotFoundException("No laboratory found with Name: " + name);

        return new ResponseEntity<>(optionalLaboratory.get(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Laboratory>> allLaboratories() {
        return new ResponseEntity<>(laboratoryService.findAll(), HttpStatus.OK);
    }
}
