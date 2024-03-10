package ro.etataru.storemgmt.web.dtos;

public record Product(
        String id,
        String name,
        String description,
        int price
) {
}
