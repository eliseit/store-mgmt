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

    public Product getProductById(int id) {
        return new Product();
    }

    public Product saveProduct(ro.etataru.storemgmt.web.dtos.Product product) {
        return new Product();
    }
}
