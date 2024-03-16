package ro.etataru.storemgmt.web.mappers;

import org.springframework.stereotype.Component;
import ro.etataru.storemgmt.entities.Product;
import ro.etataru.storemgmt.web.dtos.ProductDTO;

@Component
public class ProductMapper {
    public ProductDTO toDto(Product product) {
        return new ProductDTO(
                String.valueOf(product.getId()),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

    public Product toProduct(ProductDTO productDto) {
        return new Product(
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice()
        );
    }

    public Product toProductUpdate(ProductDTO productDto) {
        return new Product(
                Long.parseLong(productDto.getId()),
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice()
        );
    }
}
