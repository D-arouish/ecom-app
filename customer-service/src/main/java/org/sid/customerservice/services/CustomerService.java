package org.sid.customerservice.services;

import org.sid.customerservice.entities.Customer;

import java.util.List;

public interface CustomerService {


    Customer save(Customer customer);

    List<Customer> findAll();

    Customer findOne(Long id);

    boolean isExists(Long id);


    void delete(Long id);

    Customer partialUpdate(Long id, Customer customer);
}
