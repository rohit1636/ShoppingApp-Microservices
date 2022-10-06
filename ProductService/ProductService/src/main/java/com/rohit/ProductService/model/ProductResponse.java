package com.rohit.ProductService.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponse {
    private String productName;
    private Long productId;
    private Long quantity;
    private Long price;
}
