package com.micro.ProductService.service;

import com.micro.ProductService.model.ProductRequest;
import com.micro.ProductService.model.ProductResponse;

import java.util.List;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long id, long quantity);

    void deletedProduct(long id);

    List<ProductResponse> findAll();

}
