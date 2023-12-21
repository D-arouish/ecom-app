package org.sid.customerservice;

import org.sid.customerservice.config.GlobalConfig;
import org.sid.customerservice.entities.Customer;
import org.sid.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(GlobalConfig.class)
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            CustomerRepository customerRepository,
            RepositoryRestConfiguration repositoryRestConfiguration) {
        return args -> {
            repositoryRestConfiguration.exposeIdsFor(Customer.class);
            customerRepository.saveAll(
                    List.of(
                            Customer.builder()
                                    .name("Darouich")
                                    .email("Zackdarouich@gmail.com")
                                    .build(),
                            Customer.builder()
                                    .name("Zakaria")
                                    .email("Zackdarouich@gmail.com")
                                    .build(),
                            Customer.builder()
                                    .name("Anass")
                                    .email("AssalAnass@gmail.com")
                                    .build()
                            )
            );
            customerRepository.findAll().forEach(System.out::println);
            //customerRepository.findAll().forEach(c-> System.out.println(c));
        };
    }
}
