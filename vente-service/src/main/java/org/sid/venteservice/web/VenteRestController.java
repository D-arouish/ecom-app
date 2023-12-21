package org.sid.venteservice.web;

import org.sid.venteservice.entities.Vente;
import org.sid.venteservice.repositories.VenteRepository;
import org.sid.venteservice.repositories.ProductItemsRepository;
import org.sid.venteservice.service.CustomerRestClient;
import org.sid.venteservice.service.ProductRestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VenteRestController {
    private VenteRepository venteRepository;
    private ProductItemsRepository productItemsRepository;
    private CustomerRestClient customerRestClient;
    private ProductRestClient productRestClient;

    public VenteRestController(VenteRepository venteRepository,
                               ProductItemsRepository productItemsRepository,
                               CustomerRestClient customerRestClient,
                               ProductRestClient productRestClient
    ) {
        this.venteRepository = venteRepository;
        this.productItemsRepository = productItemsRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }

    @GetMapping("/fullBill/{id}")
    public Vente bill(@PathVariable Long id){
        Vente vente = venteRepository.findById(id).get();
        vente.setCustomer(customerRestClient.findCustomerById(vente.getCustomerId()));
        vente.getProductItems().forEach(
                productItem -> {
                    productItem.setProduct(productRestClient.findProductById(productItem.getProductId()));
                }
        );
        return vente;

    }

}

























