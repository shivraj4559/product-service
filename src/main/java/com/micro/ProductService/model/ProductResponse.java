package com.micro.ProductService.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

    private String productName;
    private long productId;
    private long quantity;
    private long price;

    public ProductResponse() {
    }

    public ProductResponse(String productName, long productId, long quantity, long price) {
        this.productName = productName;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
