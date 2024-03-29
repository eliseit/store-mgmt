package ro.etataru.storemgmt.web.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.etataru.storemgmt.services.ProductService;
import ro.etataru.storemgmt.web.dtos.PriceUpdateForProductDTO;
import ro.etataru.storemgmt.web.dtos.ProductDTO;
import ro.etataru.storemgmt.web.errorhandling.ProductNotFoundException;
import ro.etataru.storemgmt.web.mappers.ProductMapper;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@Slf4j
public class ProductsController {

    ProductService productService;
    ProductMapper productMapper;

    public ProductsController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_VIEWER')")
    public List<ProductDTO> getAllProducts() {
        log.info("All products request!");

        return productService.getAllProducts().stream()
                .map(p -> productMapper.toDto(p))
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_VIEWER')")
    public EntityModel<ProductDTO> getProductById(@PathVariable long id) {
        log.info("Product by id request!");

        ro.etataru.storemgmt.entities.Product product = productService.getProductById(id);

        if (null == product) {
            throw new ProductNotFoundException("Product with id: " + id + " not found.");
        }

        EntityModel<ProductDTO> entityModel = EntityModel.of(productMapper.toDto(product));

        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllProducts());
        entityModel.add(link.withRel("all-products"));

        return entityModel;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) {
        log.info("Creating product: " + product);

        ProductDTO savedProduct = productMapper.toDto(
                productService.saveProduct(
                        productMapper.toProduct(product)
                )
        );

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProduct.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO product) {
        log.info("Update product: " + product);

        ProductDTO savedProduct = productMapper.toDto(
                productService.updateProduct(
                        productMapper.toProductUpdate(product)
                )
        );

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProduct.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> updatePriceForProduct(@RequestBody PriceUpdateForProductDTO priceUpdateForProductDTO) {
        log.info("Updating price for product: " + priceUpdateForProductDTO);

        ProductDTO updatedProduct = productMapper.toDto(
                productService.updatePriceOnProduct(priceUpdateForProductDTO)
        );

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedProduct.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteProductById(@PathVariable long id) {
        log.info("Delete product with id: " + id);

        productService.deleteProductById(id);
    }

}
