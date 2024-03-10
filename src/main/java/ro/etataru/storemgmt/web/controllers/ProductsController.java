package ro.etataru.storemgmt.web.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.etataru.storemgmt.services.ProductService;
import ro.etataru.storemgmt.web.dtos.Product;
import ro.etataru.storemgmt.web.mappers.ProductMapper;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@Slf4j
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
        log.info("All products request!");

        return productService.getAllProducts().stream()
                .map(p -> productMapper.toDto(p))
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_VIEWER')")
    public Product getProductById(@PathVariable int id) {
        log.info("Product by id request!");

        return productMapper.toDto(productService.getProductById(id));
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productMapper.toDto(productService.saveProduct(product));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProduct.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
