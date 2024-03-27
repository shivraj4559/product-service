package com.micro.ProductService.service;

import com.micro.ProductService.entity.Product;
import com.micro.ProductService.exception.ProductServiceCustomException;
import com.micro.ProductService.model.ProductRequest;
import com.micro.ProductService.model.ProductResponse;
import com.micro.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {

        log.info("adding product.....");
        Product product = Product.builder()
                .productName(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity()).build();

        productRepository.save(product);
        log.info("Product Created.....");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("fetching up the product with [{}]",productId);
        Product product = productRepository.findById(productId).orElseThrow(()->new ProductServiceCustomException("Product Not found","PRODUCT_NOT_FOUND"));
        ProductResponse productResponse = ProductResponse.builder()
                .productName(product.getProductName())
                .productId(productId)
                .price(product.getPrice())
                .quantity(product.getQuantity()).build();
        return productResponse;
    }

    @Override
    public void reduceQuantity(long id, long quantity) {
        log.info("reducing quantity: {} for Id :{}",id,quantity);
        Product product = productRepository.findById(id).orElseThrow(()->new ProductServiceCustomException("Product not found","PRODUCT_NOT_FOUND"));
        if(product.getQuantity()<quantity){
            throw new ProductServiceCustomException("Don't have sufficient quantity","INSUFFICIENT_QUANTITY");
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product Quantity updated");
    }

    @Override
    public void deletedProduct(long id) {
        log.info("deleting product with Id :{}",id);
        Product product = productRepository.findById(id).orElseThrow(()->
                new ProductServiceCustomException("Product not found","PRODUCT_NOT_FOUND"));
        productRepository.delete(product);
        log.info("product deleted successfully");
    }

    @Override
    public List<ProductResponse> findAll() {
        log.info("fetching up all products");
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();
        products.forEach(product->{
            ProductResponse productResponse = ProductResponse.builder()
                    .productName(product.getProductName())
                    .productId(product.getProductId())
                    .price(product.getPrice())
                    .quantity(product.getQuantity()).build();
            productResponses.add(productResponse);
        });
        return productResponses;
    }
}
