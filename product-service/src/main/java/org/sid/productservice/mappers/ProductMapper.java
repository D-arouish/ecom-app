package org.sid.productservice.mappers;

import org.modelmapper.ModelMapper;
import org.sid.productservice.dto.ProductDTO;
import org.sid.productservice.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements Mapper<Product, ProductDTO> {

    ModelMapper modelMapper;

    ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Product mapFrom(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    @Override
    public ProductDTO mapTo(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
}
