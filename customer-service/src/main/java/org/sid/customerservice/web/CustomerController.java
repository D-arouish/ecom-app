package org.sid.customerservice.web;

import org.sid.customerservice.dto.CustomerDTO;
import org.sid.customerservice.entities.Customer;
import org.sid.customerservice.mappers.Mapper;
import org.sid.customerservice.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class CustomerController {

    private CustomerService customerService;
    private Mapper<Customer, CustomerDTO> customerDTOMapper;

    public CustomerController(
            CustomerService customerService,
            Mapper<Customer, CustomerDTO> customerDTOMappe
                              ){
        this.customerService = customerService;
        this.customerDTOMapper = customerDTOMappe;
    }


    @PostMapping(path = "/customers")
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customer){
        Customer customer1 = customerDTOMapper.mapFrom(customer);
        Customer customer2 = customerService.save(customer1);
        return customerDTOMapper.mapTo(customer2);
    }

    @GetMapping(path = "/customers")
    public List<CustomerDTO>  allCustomers(){
        List<Customer> customers = customerService.findAll();
        return  customers.stream()
                .map(customerDTOMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable("id") Long id){
        Customer customer = customerService.findOne(id);
        return customerDTOMapper.mapTo(customer);
    }

    @PutMapping(path = "/customers/{id}")
    public ResponseEntity<CustomerDTO> fullUpdate(
            @PathVariable("id") Long id,
            @RequestBody CustomerDTO customerDTO
    ) {
        if (!customerService.isExists(id)) {
            return ResponseEntity.notFound().build();
        }

        customerDTO.setId(id);
        Customer customer = customerDTOMapper.mapFrom(customerDTO);
        Customer customerSave = customerService.save(customer);
        return ResponseEntity.ok(customerDTOMapper.mapTo(customerSave));
    }

    @DeleteMapping(path = "/customers/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        customerService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/customers/{id}")
    public ResponseEntity<CustomerDTO> partialUpdate(
            @PathVariable("id") Long id,
            @RequestBody CustomerDTO customerDTO){

        if (!customerService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerDTO.setId(id);
        Customer customer = customerDTOMapper.mapFrom(customerDTO);
        Customer update = customerService.partialUpdate(id, customer);
        return new ResponseEntity<>(customerDTOMapper.mapTo(update),HttpStatus.OK);
    }

}


















