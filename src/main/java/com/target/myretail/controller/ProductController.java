package com.target.myretail.controller;

import com.target.myretail.model.Product;
import com.target.myretail.model.ProductPrice;
import com.target.myretail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Main controller exposing REST endpoints to GET a Product by id, and
 * to PUT an update to a product's price info.
 *
 */
@RestController
@Validated
public class ProductController {

    @Autowired
    ProductService service;

    /**
     * GET a product by id.
     * @param id
     * @return
     */
    @GetMapping(value = "/products/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> getProduct(@PathVariable @Positive( message="id path variable must be a number > 0") long id) {
        Product product = service.getProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Update a product price (amount and currency code).
     * @param id
     * @param product
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/products/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> updateProductPrice(@PathVariable @Positive( message="id path variable must be a number > 0") long id,@Valid @RequestBody Product product) {
        Product productResult = service.updateProduct(id, product);
        return new ResponseEntity<>(productResult, HttpStatus.OK);
    }

    /**
     * GET all Products. Used just for initial testing purposes, with small data sets.
     * will be removed in higher test envs.
     * @return
     */
    @GetMapping(value = "/products/allProducts",produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ProductPrice> getAllProductPrices() {
        return service.getAllProductPrices();
    }

}