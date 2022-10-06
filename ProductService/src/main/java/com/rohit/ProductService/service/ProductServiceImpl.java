package com.rohit.ProductService.service;

import com.rohit.ProductService.entity.Product;
import com.rohit.ProductService.exception.ProductServiceCustomException;
import com.rohit.ProductService.model.ProductRequest;
import com.rohit.ProductService.model.ProductResponse;
import com.rohit.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepo;
    @Override
    public Long addProduct(ProductRequest productRequest) {
        log.info("Adding product..");
        Product p = Product.builder().productName(productRequest.getName()).price(productRequest.getPrice())
                        .quantity(productRequest.getQuantity()).build();
        productRepo.save(p);
        log.info("Product created..");
        return p.getProductId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("Get the product for product ID ",productId);
        Product product = productRepo.findById(productId).orElseThrow(()-> new ProductServiceCustomException("Product with given id not found","PRODUCT_NOT_FOUND"));

        // We can copy the properties using the below two methods one is through builder and the other is through bean utils.
//        ProductResponse productResponse = ProductResponse.builder().productId(product.getProductId())
//                .productName(product.getProductName())
//                .price(product.getPrice()).quantity(product.getQuantity()).build();

        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product,productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(Long productId, Long quantity) {
        log.info("Reduce quantity {} for id {}",quantity,productId);
        Product product = productRepo.findById(productId).orElseThrow(()-> new ProductServiceCustomException("Product with given id not found","PRODUCT_NOT_FOUND"));
        if(product.getQuantity() < quantity){
            throw new ProductServiceCustomException("Product does not have sufficient quantity","INSUFFICIENT_QUANTITY");
        }
        product.setQuantity(product.getQuantity()-quantity);
        productRepo.save(product);
        log.info("Product Quantity updated successfully");
    }
}
