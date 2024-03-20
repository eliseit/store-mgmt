package ro.etataru.storemgmt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userId;
    private LocalDateTime orderDate;
    @OneToMany(mappedBy = "id")
    private List<Product> products;

    public Order() {
    }

    public Order(String userId, LocalDateTime orderDate, List<Product> products) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.products = products;
    }
}
