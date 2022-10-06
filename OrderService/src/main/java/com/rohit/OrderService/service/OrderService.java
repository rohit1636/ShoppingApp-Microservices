package com.rohit.OrderService.service;

import com.rohit.OrderService.model.OrderRequest;

public interface OrderService {
    Long placeOrder(OrderRequest orderRequest);
}
