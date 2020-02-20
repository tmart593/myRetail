package com.target.myretail.service;


import com.target.myretail.exception.DataInputException;
import com.target.myretail.model.*;
import com.target.myretail.repository.ProductPriceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductPriceRepository repository;

    private ProductInfoService productInfoService;

    private ProductPriceService productPriceService;

    public ProductService(ProductPriceRepository repository, ProductInfoService productInfoService,
                          ProductPriceService productPriceService) {
        this.repository = repository;
        this.productInfoService = productInfoService;
        this.productPriceService = productPriceService;
    }

    public Product getProduct(long id){
            ProductInfo productInfo= productInfoService.getProductInfo(id);
            ProductPrice productPrice = productPriceService.getProductPrice(id);
            Product product = new Product();
            product.setId(id);
            product.setName(productInfo.getName());
            CurrentPrice currentPrice= new CurrentPrice();
            currentPrice.setCurrencyCode(productPrice.getCurrencyCode().getCode());
            currentPrice.setValue(productPrice.getValue());
            product.setCurrentPrice(currentPrice);
        return product;
    }

    public Product updateProduct(long id, Product product){
        if (id != product.getId()){
            throw new DataInputException("id in request body conflicts with id in resource path");
        }
        productInfoService.getProductInfo(id);
        ProductPrice productPrice = productPriceService.getProductPrice(id);
        productPrice.setValue(product.getCurrentPrice().getValue());
        productPrice.setCurrencyCode(CurrencyCode.valueOf(product.getCurrentPrice().getCurrencyCode()));
        repository.save(productPrice);
        return product;
    }

    public List<ProductPrice> getAllProductPrices() {
        return productPriceService.getAllProductPrices();

    }
}
