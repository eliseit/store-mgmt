package ro.etataru.storemgmt.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.etataru.storemgmt.entities.Order;
import ro.etataru.storemgmt.entities.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order getOrderById(long id) {
        return repository.findById(id).orElse(null);
    }

    public Order saveOrder(Order order) {
        log.info("Saving order: " + order);

        return repository.save(order);
    }

    public void deleteOrderById(long id) {
        repository.deleteById(id);
    }

    public Order updateOrder(Order orderUpdate) {
        log.info("Update order: " + orderUpdate);

        Optional<Order> existingOrder = repository
                .findById(orderUpdate.getId());

        existingOrder.ifPresent(o -> {
            o.setOrderDate(orderUpdate.getOrderDate());
            o.setProducts(orderUpdate.getProducts());
            repository.save(o);
        });

        return existingOrder.orElse(null);
    }
}
