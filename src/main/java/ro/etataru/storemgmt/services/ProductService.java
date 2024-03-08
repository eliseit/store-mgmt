package ro.etataru.storemgmt.services;

import org.springframework.stereotype.Service;
import ro.etataru.storemgmt.entities.Product;

import java.util.List;

@Service
public class ProductService {
    public List<Product> getAllProducts() {
        return List.of(
                new Product(),
                new Product()
        );
    }
}
