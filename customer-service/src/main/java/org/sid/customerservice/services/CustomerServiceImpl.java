package org.sid.customerservice.services;


import org.sid.customerservice.entities.Customer;
import org.sid.customerservice.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer) ;
    }

    @Override
    public List<Customer> findAll() {
        return StreamSupport.stream(customerRepository
                .findAll()
                .spliterator(),false).collect(Collectors.toList());
    }

    @Override
    public Customer findOne(Long id) {
        Customer customer = customerRepository.findById(id).get();
        return customer;
    }

    @Override
    public boolean isExists(Long id) {
        return customerRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer partialUpdate(Long id, Customer customer) {
        customer.setId(id);
        return  customerRepository.findById(id).map(existingCustomer ->{
            Optional.ofNullable(customer.getName()).ifPresent(existingCustomer::setName);
            Optional.ofNullable(customer.getEmail()).ifPresent(existingCustomer::setEmail);
            return customerRepository.save(existingCustomer);
        }).orElseThrow(()-> new RuntimeException("Customer does not exist"));
    }
}