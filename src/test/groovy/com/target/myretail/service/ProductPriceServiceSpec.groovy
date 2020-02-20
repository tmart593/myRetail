package com.target.myretail.service

import com.target.myretail.exception.ProductNotFoundException
import com.target.myretail.exception.ServiceException
import com.target.myretail.model.CurrencyCode
import com.target.myretail.model.ProductPrice
import org.springframework.dao.DataAccessException;
import com.target.myretail.repository.ProductPriceRepository
import org.springframework.dao.DataAccessException
import spock.lang.Specification

class ProductPriceServiceSpec extends Specification {
    def "GetProductPrice"() {

        setup:
        ProductPriceRepository productPriceRepository =Mock(ProductPriceRepository)
        ProductPriceService productPriceService= new ProductPriceService()
        productPriceService.productPriceRepository=productPriceRepository

        String json ='''
                {"id":13860425,"name":"Godzilla (Blu-ray)","current_price":{"value":11.78,"currency_code":"USD"}}
                       '''
        long id=101

        ProductPrice productPrice1 = new ProductPrice()
        productPrice1.setCurrencyCode(CurrencyCode.USD)
        productPrice1.setValue(11.78)

        when:
        ProductPrice productPrice= productPriceService.getProductPrice(id)

        then:
        1 * productPriceRepository.findByid(_ ) >> productPrice1
        productPrice.currencyCode==CurrencyCode.USD
        productPrice.value==11.78



    }

    def "GetProductPrice 404"() {

        setup:
        ProductPriceRepository productPriceRepository =Mock(ProductPriceRepository)
        ProductPriceService productPriceService= new ProductPriceService()
        productPriceService.productPriceRepository=productPriceRepository

        long id=101

        ProductPrice productPrice1 = null

        when:
        ProductPrice productPrice= productPriceService.getProductPrice(id)

        then:
        1 * productPriceRepository.findByid(_ ) >> productPrice1
        thrown(ProductNotFoundException)
    }


    def "GetProductPrice ServiceException"() {

        setup:
        ProductPriceRepository productPriceRepository =Mock(ProductPriceRepository)
        ProductPriceService productPriceService= new ProductPriceService()
        productPriceService.productPriceRepository=productPriceRepository

        DataAccessException dataAccessException = Mock(DataAccessException)

        long id=101

        when:
        ProductPrice productPrice= productPriceService.getProductPrice(id)

        then:
        1 * productPriceRepository.findByid(_ ) >> {throw dataAccessException}
        thrown(ServiceException)
    }



}
