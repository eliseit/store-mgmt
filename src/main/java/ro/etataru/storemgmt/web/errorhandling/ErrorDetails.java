package ro.etataru.storemgmt.web.errorhandling;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timestamp, String message, String description) {
}
