package org.sid.customerservice.mappers;

import org.modelmapper.ModelMapper;
import org.sid.customerservice.dto.CustomerDTO;
import org.sid.customerservice.entities.Customer;
import org.sid.customerservice.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements Mapper<Customer, CustomerDTO> {
    private ModelMapper modelMapper;

    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerDTO mapTo(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public Customer mapFrom(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }
}
