package br.com.api.services;

import br.com.api.exceptions.ResourceNotFoundException;
import br.com.api.models.Contract;
import br.com.api.models.Laboratory;
import br.com.api.models.Property;
import br.com.api.models.User;
import br.com.api.models.dtos.CreateContractDto;
import br.com.api.repositories.ContractRepository;
import br.com.api.repositories.LaboratoryRepository;
import br.com.api.repositories.PropertyRepository;
import br.com.api.repositories.UserRepository;
import br.com.api.services.interfaces.ContractService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final LaboratoryRepository laboratoryRepository;

    public ContractServiceImpl(ContractRepository contractRepository, UserRepository userRepository, PropertyRepository propertyRepository, LaboratoryRepository laboratoryRepository) {
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.laboratoryRepository = laboratoryRepository;
    }

    @Override
    public Contract create(Contract contract) {
        contract.setCode(generateCode());

        return contractRepository.save(contract);
    }

    @Override
    public Contract update(Integer id, CreateContractDto createContractDto) throws ResourceNotFoundException {
        Optional<User> optionalUser = userRepository.findById(createContractDto.getUserId());

        if (!optionalUser.isPresent())
            throw new ResourceNotFoundException("No user found with the Id: " + createContractDto.getUserId());

        Optional<Property> optionalProperty = propertyRepository.findById(createContractDto.getPropertyId());

        if (!optionalProperty.isPresent())
            throw new ResourceNotFoundException("No property found with the Id: " + createContractDto.getPropertyId());

        Optional<Laboratory> optionalLaboratory = laboratoryRepository.findById(createContractDto.getLaboratoryId());

        if (!optionalLaboratory.isPresent())
            throw new ResourceNotFoundException("No laboratory found with the Id: " + createContractDto.getLaboratoryId());

        Optional<Contract> optionalContract = contractRepository.findById(id);

        if (!optionalContract.isPresent())
            throw new ResourceNotFoundException("No contract found with the Id: " + id);

        final Contract contractToUpdate = optionalContract.get();
        contractToUpdate.setFinalDate(createContractDto.getFinalDate());
        contractToUpdate.setInitialDate(createContractDto.getInitialDate());
        contractToUpdate.setUser(optionalUser.get());
        contractToUpdate.setProperty(optionalProperty.get());
        contractToUpdate.setLaboratory(optionalLaboratory.get());
        contractToUpdate.setNote(createContractDto.getNote());

        return contractRepository.save(contractToUpdate);
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException {
        Optional<Contract> optionalContract = findById(id);

        if (!optionalContract.isPresent())
            throw new ResourceNotFoundException("No contract found with the Id: " + id);

        contractRepository.deleteById(id);
    }

    @Override
    public List<Contract> findAll() {
        List<Contract> contracts = new ArrayList<>();

        contractRepository.findAll().forEach(contracts::add);

        return contracts;
    }

    @Override
    public Optional<Contract> findById(Integer id) {
        return contractRepository.findById(id);
    }

    @Override
    public Optional<Contract> findByCode(String code) {
        return contractRepository.findByCode(code);
    }

    private String generateCode() {
        Optional<Contract> contract = contractRepository.findFirstByOrderByIdDesc();

        int lastId = contract.map(Contract::getId).orElse(0);

        return "CT-" + Calendar.getInstance().get(Calendar.YEAR) + "-" + (1000 + lastId + 1);
    }
}
