package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dtos.CustomerSeedDto;
import exam.model.entities.Customer;
import exam.model.entities.Town;
import exam.repository.CustomerRepository;
import exam.service.CustomerService;
import exam.service.TownService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMERS_FILE_PATH = "src/main/resources/files/json/customers.json";

    private final CustomerRepository customerRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final TownService townService;

    public CustomerServiceImpl(CustomerRepository customerRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, TownService townService) {
        this.customerRepository = customerRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.townService = townService;
    }


    @Override
    public boolean areImported() {
        return customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of(CUSTOMERS_FILE_PATH));
    }

    @Override
    public String importCustomers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        CustomerSeedDto[] customerDtos = gson.fromJson(readCustomersFileContent(), CustomerSeedDto[].class);

        Arrays
                .stream(customerDtos)
                .filter(customerSeedDto -> {
                    boolean isValid = validationUtil.isValid(customerSeedDto)
                            && !doesEntityExists(customerSeedDto.getEmail())
                            && townService.doesEntityExists(customerSeedDto.getTown().getName());

                    stringBuilder
                            .append(isValid
                                    ? String.format("Successfully imported Customer %s %s - %s",
                                    customerSeedDto.getFirstName(), customerSeedDto.getLastName(), customerSeedDto.getEmail())
                                    : "Invalid Customer")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(customerSeedDto -> {
                    Customer customer = modelMapper.map(customerSeedDto, Customer.class);

                    String townName = customerSeedDto.getTown().getName();
                    Town town = townService.findByName(townName);
                    customer.setTown(town);

                    return customer;
                })
                .forEach(customerRepository::save);


        return stringBuilder.toString();
    }

    private boolean doesEntityExists(String email) {
        return customerRepository.existsByEmail(email);
    }
}
