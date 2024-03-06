package ro.etataru.storemgmt.web.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductsController {

    @GetMapping()
    public List<String> getAllProducts() {
        return List.of("Product 1", "Product 2");
    }
}
