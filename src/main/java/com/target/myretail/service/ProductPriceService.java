package com.target.myretail.service;

import com.target.myretail.exception.ProductNotFoundException;
import com.target.myretail.exception.ServiceException;
import com.target.myretail.model.ProductPrice;
import com.target.myretail.repository.ProductPriceRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPriceService {


     private ProductPriceRepository productPriceRepository;

    public ProductPriceService(ProductPriceRepository productPriceRepository) {
        this.productPriceRepository = productPriceRepository;
    }

    public ProductPrice getProductPrice(long id){
        ProductPrice productPrice=null;
        try{
            productPrice=productPriceRepository.findByid(id);
            if (productPrice==null){
                throw new ProductNotFoundException("could not find product data for id: "+id);
            }
        } catch(DataAccessException e) {
            throw new ServiceException("could not retrieve product data for id: " + id);
        }

        return productPrice;
    }


    /**
     * This class is mainly for initial testing in lower environments.
     * @return
     */
    public List<ProductPrice> getAllProductPrices(){
        return productPriceRepository.findAll();
    }


}
