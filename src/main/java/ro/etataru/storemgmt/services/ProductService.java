package ro.etataru.storemgmt.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.etataru.storemgmt.entities.Product;
import ro.etataru.storemgmt.entities.ProductRepository;
import ro.etataru.storemgmt.web.dtos.PriceUpdateForProductDTO;

import java.util.List;
import java.util.Optional;

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

    public void deleteProductById(long id) {
        repository.deleteById(id);
    }

    public Product updatePriceOnProduct(PriceUpdateForProductDTO priceUpdateForProductDTO) {

        Optional<Product> product = repository
                .findById(
                        Long.valueOf(priceUpdateForProductDTO.getId()));

        product.ifPresent(p -> {
            p.setPrice(priceUpdateForProductDTO.getNewPrice());
            repository.save(p);
        });

        return product.orElse(null);
    }

    public Product updateProduct(Product product) {
        log.info("Update product: " + product);

        Optional<Product> existingProduct = repository
                .findById(product.getId());

        existingProduct.ifPresent(p -> {
            if (!p.getDescription().equalsIgnoreCase(product.getDescription())) {
                p.setDescription(product.getDescription());
            }

            if (!p.getName().equalsIgnoreCase(product.getName())) {
                p.setName(product.getName());
            }

            if (p.getPrice() != product.getPrice()) {
                p.setPrice(product.getPrice());
            }

            repository.save(p);
        });

        return existingProduct.orElse(null);
    }
}
