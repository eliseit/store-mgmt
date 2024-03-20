package ro.etataru.storemgmt.web.mappers;

import org.springframework.stereotype.Component;
import ro.etataru.storemgmt.entities.Order;
import ro.etataru.storemgmt.web.dtos.OrderDTO;

import static java.util.stream.Collectors.toList;

@Component
public class OrderMapper {

    final
    ProductMapper productMapper;

    public OrderMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public OrderDTO toDto(Order order) {
        return new OrderDTO(
                String.valueOf(order.getId()),
                order.getUserId(),
                order.getOrderDate(),
                order.getProducts().stream()
                        .map(productMapper::toDto)
                        .collect(toList())
        );
    }

    public Order toOrder(OrderDTO orderDto) {
        return new Order(
                orderDto.getUserId(),
                orderDto.getOrderDate(),
                orderDto.getProducts().stream()
                        .map(productMapper::toProduct)
                        .collect(toList())
        );
    }

    public Order toOrderUpdate(OrderDTO orderDto) {
        return new Order(
                Long.parseLong(orderDto.getId()),
                orderDto.getUserId(),
                orderDto.getOrderDate(),
                orderDto.getProducts().stream()
                        .map(productMapper::toProduct)
                        .collect(toList())
        );
    }
}
