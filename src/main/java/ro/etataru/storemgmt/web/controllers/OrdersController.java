package ro.etataru.storemgmt.web.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.etataru.storemgmt.services.OrderService;

import ro.etataru.storemgmt.web.dtos.OrderDTO;
import ro.etataru.storemgmt.web.errorhandling.OrderNotFoundException;
import ro.etataru.storemgmt.web.mappers.OrderMapper;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@Slf4j
public class OrdersController {

    OrderService orderService;
    OrderMapper orderMapper;

    public OrdersController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_VIEWER')")
    public List<OrderDTO> getAllOrders() {
        log.info("All orders request!");

        return orderService.getAllOrders().stream()
                .map(p -> orderMapper.toDto(p))
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_VIEWER')")
    public EntityModel<OrderDTO> getOrderById(@PathVariable long id) {
        log.info("Order by id request!");

        ro.etataru.storemgmt.entities.Order order = orderService.getOrderById(id);

        if (null == order) {
            throw new OrderNotFoundException("Order with id: " + id + " not found.");
        }

        EntityModel<OrderDTO> entityModel = EntityModel.of(orderMapper.toDto(order));

        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllOrders());
        entityModel.add(link.withRel("all-orders"));

        return entityModel;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order) {
        log.info("Creating order: " + order);

        OrderDTO savedOrder = orderMapper.toDto(
                orderService.saveOrder(
                        orderMapper.toOrder(order)
                )
        );

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedOrder.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO order) {
        log.info("Update order: " + order);

        OrderDTO savedOrder = orderMapper.toDto(
                orderService.updateOrder(
                        orderMapper.toOrderUpdate(order)
                )
        );

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedOrder.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteOrderById(@PathVariable long id) {
        log.info("Delete order with id: " + id);

        orderService.deleteOrderById(id);
    }

}
