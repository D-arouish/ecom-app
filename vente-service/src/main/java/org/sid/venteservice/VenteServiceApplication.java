package org.sid.venteservice;

import org.sid.venteservice.entities.Vente;
import org.sid.venteservice.entities.ProductItem;
import org.sid.venteservice.model.Customer;
import org.sid.venteservice.model.Product;
import org.sid.venteservice.repositories.VenteRepository;
import org.sid.venteservice.repositories.ProductItemsRepository;
import org.sid.venteservice.service.CustomerRestClient;
import org.sid.venteservice.service.ProductRestClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients //to enable injection and recognize customer and product rest controller
public class VenteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VenteServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(VenteRepository venteRepository,
                            ProductItemsRepository productItemsRepository,
                            CustomerRestClient customerRestClient,
                            ProductRestClient productRestClient) {
        return  args -> {

            Collection<Product> products = productRestClient.allProducts().getContent();
            Long customerId=1L;
            Customer customer = customerRestClient.findCustomerById(customerId);
            if(customer == null) throw  new RuntimeException("Customer not found");
            Vente vente = new Vente();
            vente.setVenteDate(new Date());
            vente.setCustomerId(customerId);
            Vente savedVente = venteRepository.save(vente);
            products.forEach(
                    product -> {
                        ProductItem productItem = new ProductItem();
                        productItem.setVente(savedVente);
                        productItem.setQuantity(1+new Random().nextInt(10));
                        productItem.setPrice(product.getPrice());
                        productItem.setDiscount(Math.random());
                        productItem.setProductId(product.getId());
                        productItemsRepository.save(productItem);
                    }
            );
        };
    }
}



























