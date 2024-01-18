package org.sid.venteservice.web;

import org.sid.venteservice.entities.ProductItem;
import org.sid.venteservice.entities.Vente;

import org.sid.venteservice.model.Customer;
import org.sid.venteservice.model.Product;
import org.sid.venteservice.repositories.VenteRepository;
import org.sid.venteservice.repositories.ProductItemsRepository;
import org.sid.venteservice.service.CustomerRestClient;
import org.sid.venteservice.service.ProductRestClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class VenteRestController {
    private final VenteRepository venteRepository;
    private final ProductItemsRepository productItemsRepository;
    private final CustomerRestClient customerRestClient;
    private final ProductRestClient productRestClient;
    //private KafkaTemplate<String, SalesEvent> kafkaTemplate;


    public VenteRestController(VenteRepository venteRepository,
                               ProductItemsRepository productItemsRepository,
                               CustomerRestClient customerRestClient,
                               ProductRestClient productRestClient
    //                           KafkaTemplate kafkaTemplate
    ) {
        this.venteRepository = venteRepository;
        this.productItemsRepository = productItemsRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    //    this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/fullBill/{id}")
    public Vente bill(@PathVariable Long id){
        Vente vente = venteRepository.findById(id).get();
        vente.setCustomer(customerRestClient.findCustomerById(vente.getCustomerId()));
        vente.getProductItems().forEach(
                productItem -> productItem.setProduct(productRestClient.findProductById(productItem.getProductId()))
        );
        return vente;

    }



    @GetMapping(path = "/sales")
    public List<Vente> allSales() {
        List<Vente> ventes = venteRepository.findAll();

        // If you need to fetch additional information for each vente, you can do it here
        ventes.forEach(vente -> {
            vente.setCustomer(customerRestClient.findCustomerById(vente.getCustomerId()));
            vente.getProductItems().forEach(
                    productItem -> productItem.setProduct(productRestClient.findProductById(productItem.getProductId()))
            );
        });

        return ventes;
    }

    @GetMapping(path = "/sales/{customerId}")
    public ResponseEntity<List<Vente>> ventesByCustomer(@PathVariable("customerId") Long customerId) {
        // Validate the customer ID
        if (customerId == null) {
            return ResponseEntity.badRequest().build();
        }

        // Fetch ventes based on the customer ID
        List<Vente> ventes = venteRepository.findByCustomerId(customerId);

        // If ventes are found, return them
        if (!ventes.isEmpty()) {
            // Fetch additional information for each vente if needed
            ventes.forEach(vente -> {
                vente.setCustomer(customerRestClient.findCustomerById(vente.getCustomerId()));
                vente.getProductItems().forEach(productItem ->
                        productItem.setProduct(productRestClient.findProductById(productItem.getProductId()))
                );
            });

            return ResponseEntity.ok(ventes);
        } else {
            // If no ventes are found, return a not found response
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping(path = "/sales/{id}")
    public ResponseEntity<String> deleteVente(@PathVariable("id") Long id) {
        // Validate the ID
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body("Invalid Vente ID");
        }

        // Check if the Vente exists
        Optional<Vente> vente = venteRepository.findById(id);
        if (vente.isPresent()) {
            // Delete associated product items
            productItemsRepository.deleteByVente(vente.get());

            // If the Vente exists, delete it
            venteRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            // If the Vente does not exist, return a not found response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vente not found");
        }
    }


    @DeleteMapping(path = "/productItem/{id}")
    public void deleteProductItem(@PathVariable("id") Long id) {
        productItemsRepository.deleteById(id);
    }


    @PostMapping(path = "/sales")
    public ResponseEntity<String> addVenteWithProducts(@RequestBody Vente newVente) {

        Customer customer = customerRestClient.findCustomerById(newVente.getCustomerId());
        if (customer == null) throw new RuntimeException("customer not found");
        Vente vente = new Vente();
        vente.setVenteDate(new Date());
        vente.setCustomerId(customer.getId());
        Vente savedVente = venteRepository.save(vente);
        newVente.getProductItems().forEach(item -> {
            Long productId = item.getProductId();
            int requestedQuantity = item.getQuantity();

            Product product = productRestClient.findProductById(productId);
            if (product == null) throw new RuntimeException("Product not found for ID:" + productId);
            // Check if the requested quantity is available
            if (product.getQuantity() < requestedQuantity) throw new RuntimeException("Insufficient quantity for product ID: " + productId);

            // Update product quantity
            product.setQuantity(product.getQuantity() - requestedQuantity);
            productRestClient.updateProduct(product.getId(),product);

            ProductItem productItem = new ProductItem();
            productItem.setVente(savedVente);
            productItem.setPrice(product.getPrice() * requestedQuantity);
            productItem.setQuantity(item.getQuantity());
            productItem.setDiscount(Math.random());
            productItem.setProductId(productId);
            productItemsRepository.save(productItem);


        });
        //kafkaTemplate.send("notificationTopic", new SalesEvent(savedVente.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body("Vente with products added successfully");
    }



}