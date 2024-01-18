package org.sid.productservice.controller;



import org.sid.productservice.dto.ProductDTO;
import org.sid.productservice.entities.Product;
import org.sid.productservice.mappers.ProductMapper;
import org.sid.productservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class ProductController {
    ProductService productService;
    ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping(path = "/products")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO){
        Product product = productMapper.mapFrom(productDTO);
        Product createdProduct = productService.save(product);
        return productMapper.mapTo(createdProduct);

    }

    @GetMapping(path = "/products")
    public List<ProductDTO> allProducts(){
        List<Product>  products= productService.findAll();
        return products.stream().map(productMapper::mapTo)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity delete (@PathVariable("id") Long id){
        productService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }


    @GetMapping(path = "/products/{id}")
    public ProductDTO getProduct(@PathVariable("id") Long id){
        Product  product = productService.findOne(id);
        return productMapper.mapTo(product);
    }


    @PutMapping(path = "/products/{id}")
    public ResponseEntity<ProductDTO> fullUpdate(
            @PathVariable("id") Long id,
            @RequestBody ProductDTO productDTO
    ){
        if (!productService.isExists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        productDTO.setId(id);
        Product product = productMapper.mapFrom(productDTO);
        Product productSave = productService.save(product);
        return ResponseEntity.ok(productMapper.mapTo(productSave));
    }

    @PatchMapping(path = "/products/{id}")
    public ResponseEntity<ProductDTO> partialUpdate(
            @PathVariable("id") Long id,
            @RequestBody ProductDTO productDTO){

        if (!productService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productDTO.setId(id);
        Product product = productMapper.mapFrom(productDTO);
        Product update = productService.partialUpdate(id, product);
        return new ResponseEntity<>(productMapper.mapTo(update),HttpStatus.OK);
    }





}
