package ro.etataru.storemgmt.web.dtos;

import lombok.Data;

@Data
public class PriceUpdateForProductDTO {
    private String id;
    private Long newPrice;
}
