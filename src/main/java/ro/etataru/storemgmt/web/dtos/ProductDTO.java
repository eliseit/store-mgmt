package ro.etataru.storemgmt.web.dtos;

public record ProductDTO(
        String id,
        String name,
        String description,
        long price
) {
}
