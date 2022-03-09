package br.com.api.controllers;

import br.com.api.exceptions.ResourceNotFoundException;
import br.com.api.models.Contract;
import br.com.api.models.Laboratory;
import br.com.api.models.Property;
import br.com.api.models.User;
import br.com.api.models.dtos.CreateContractDto;
import br.com.api.services.interfaces.ContractService;
import br.com.api.services.interfaces.LaboratoryService;
import br.com.api.services.interfaces.PropertyService;
import br.com.api.services.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Optional;

@Validated
@RequestMapping(value = "/contracts")
@RestController
public class ContractController {

    private final ContractService contractService;
    private final UserService userService;
    private final PropertyService propertyService;
    private final LaboratoryService laboratoryService;

    public ContractController(ContractService contractService, UserService userService, PropertyService propertyService, LaboratoryService laboratoryService) {
        this.contractService = contractService;
        this.userService = userService;
        this.propertyService = propertyService;
        this.laboratoryService = laboratoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<Contract> createContract(@Valid @RequestBody CreateContractDto createContractDto) throws ResourceNotFoundException {
        Optional<User> optionalUser = userService.findById(createContractDto.getUserId());

        if (!optionalUser.isPresent())
            throw new ResourceNotFoundException("No user found with the Id: " + createContractDto.getUserId());

        Optional<Property> optionalProperty = propertyService.findById(createContractDto.getPropertyId());

        if (!optionalProperty.isPresent())
            throw new ResourceNotFoundException("No property found with the Id: " + createContractDto.getPropertyId());

        Optional<Laboratory> optionalLaboratory = laboratoryService.findById(createContractDto.getLaboratoryId());

        if (!optionalLaboratory.isPresent())
            throw new ResourceNotFoundException("No laboratory found with the Id: " + createContractDto.getLaboratoryId());

        Contract contractInput = createContractDto.toContract();
        contractInput.setUser(optionalUser.get());
        contractInput.setProperty(optionalProperty.get());
        contractInput.setLaboratory(optionalLaboratory.get());

        Contract createdContract = contractService.create(contractInput);

        return new ResponseEntity<>(createdContract, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Contract> updateContract(@PathVariable @NotBlank Integer id, @Valid @RequestBody CreateContractDto createContractDto) throws ResourceNotFoundException {
        final Contract updatedContract = contractService.update(id, createContractDto);

        return new ResponseEntity<>(updatedContract, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable @NotBlank Integer id) throws ResourceNotFoundException {
        contractService.delete(id);

        return new ResponseEntity<>("Contract: " + id + " deleted.", HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Contract> getContractByCode(@Pattern(regexp = "^CT(-\\d{4,}){2}$", message = "The contract code is invalid") @PathVariable @NotBlank String code) throws ResourceNotFoundException {
        Optional<Contract> optionalContract = contractService.findByCode(code);

        if (!optionalContract.isPresent()) {
            throw new ResourceNotFoundException("No contract found with the code: " + code);
        }

        return new ResponseEntity<>(optionalContract.get(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Contract>> allContracts() {
        return new ResponseEntity<>(contractService.findAll(), HttpStatus.OK);
    }
}
