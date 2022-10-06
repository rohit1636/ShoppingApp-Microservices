package com.rohit.ProductService.service;

import com.rohit.ProductService.model.ProductRequest;
import com.rohit.ProductService.model.ProductResponse;

public interface ProductService {
    Long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long productId);

    void reduceQuantity(Long productId,Long quantity);
}
