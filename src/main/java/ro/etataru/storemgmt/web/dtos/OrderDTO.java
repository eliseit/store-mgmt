package ro.etataru.storemgmt.web.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTO {

    private String id;
    private String userId;
    private LocalDateTime orderDate;
    private List<ProductDTO> products;

}
