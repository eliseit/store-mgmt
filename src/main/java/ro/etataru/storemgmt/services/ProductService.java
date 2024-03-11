package ro.etataru.storemgmt.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.etataru.storemgmt.entities.Product;
import ro.etataru.storemgmt.entities.ProductRepository;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(long id) {
        return repository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        log.info("Saving product: " + product);

        return repository.save(product);
    }
}
