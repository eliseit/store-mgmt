package ro.etataru.storemgmt.web.mappers;

import org.springframework.stereotype.Component;
import ro.etataru.storemgmt.entities.Product;

@Component
public class ProductMapper {
    public ro.etataru.storemgmt.web.dtos.Product toDto(Product product) {
        return new ro.etataru.storemgmt.web.dtos.Product("109af1928", "Fundamentals of Software Architecture", 358);
    }
}
