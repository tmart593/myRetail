package com.target.myretail.repository;


import com.target.myretail.model.ProductPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaces with the 'productprice' collection in mongo db 'myretail'.
 */
@Repository
public interface ProductPriceRepository extends MongoRepository<ProductPrice, String> {
    ProductPrice findByid(@Param("id") long id);
    List<ProductPrice> findAll();
}
