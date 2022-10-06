package com.rohit.OrderService.service;

import com.rohit.OrderService.entity.Order;
import com.rohit.OrderService.external.client.ProductService;
import com.rohit.OrderService.model.OrderRequest;
import com.rohit.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Override
    public Long placeOrder(OrderRequest orderRequest) {
        // Create Order Entity and save the status with Order Created.
        // Then call product Service to block order quantity (reduce the quantity)
        // Call Payment Service to complete the payment. If the payment is successful then mark the order status
        // as complete if not then cancelled.
        log.info("Placing order request : {}",orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());
        log.info("Creating order with status CREATED");
        Order order = Order.builder().amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .orderDate(Instant.now())
                .build();
        order = orderRepository.save(order);
        log.info("Order placed successfully with order id : {}",order.getOrderId());
        return order.getOrderId() ;
    }
}
