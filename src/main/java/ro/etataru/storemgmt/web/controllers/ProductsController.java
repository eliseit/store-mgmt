package ro.etataru.storemgmt.web.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.etataru.storemgmt.services.ProductService;
import ro.etataru.storemgmt.web.dtos.Product;
import ro.etataru.storemgmt.web.mappers.ProductMapper;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductsController {

    ProductService productService;
    ProductMapper productMapper;

    public ProductsController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_VIEWER')")
    public List<Product> getAllProducts() {

        return productService.getAllProducts().stream()
                .map(p -> productMapper.toDto(p))
                .toList();
    }
}
