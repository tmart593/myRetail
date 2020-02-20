package com.target.myretail.service

import com.target.myretail.exception.ProductNotFoundException
import com.target.myretail.exception.ServiceException
import com.target.myretail.model.ProductInfo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class ProductInfoServiceSpec extends Specification {
    def "GetProductInfo"() {
        setup:
        RestTemplate restTemplate = Mock(RestTemplate)
        ProductInfoService productInfoService= new ProductInfoService()
        productInfoService.restTemplateService=restTemplate

        String json ='''
                {"product":{"item": {"product_description":{
                "title":"test"}}}}
                       '''
        long id=101
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(json, HttpStatus.OK)

        when:
        ProductInfo productInfo= productInfoService.getProductInfo(id)

        then:
        1 * restTemplate.getForEntity(*_ ) >> responseEntity
        productInfo.name=='test'

    }

    def "GetProductInfo HttpStatusCodeException 404"() {
        setup:
        RestTemplate restTemplate = Mock(RestTemplate)
        ProductInfoService productInfoService= new ProductInfoService()
        productInfoService.restTemplateService=restTemplate

        String json ='''
                {"product":{"item": {"product_description":{
                "title":"test"}}}}
                       '''
        HttpStatusCodeException httpStatusCodeException = Mock(HttpStatusCodeException)
        httpStatusCodeException.getStatusCode() >> HttpStatus.NOT_FOUND

        long id=101
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(json, HttpStatus.OK)

        when:
        ProductInfo productInfo= productInfoService.getProductInfo(id)

        then:
        1 * restTemplate.getForEntity(*_ ) >> {throw httpStatusCodeException}
        thrown(ProductNotFoundException)

    }
    def "GetProductInfo HttpStatusCodeException 500"() {
        setup:
        RestTemplate restTemplate = Mock(RestTemplate)
        ProductInfoService productInfoService= new ProductInfoService()
        productInfoService.restTemplateService=restTemplate

        String json ='''
                {"product":{"item": {"product_description":{
                "title":"test"}}}}
                       '''
        HttpStatusCodeException httpStatusCodeException = Mock(HttpStatusCodeException)
        httpStatusCodeException.getStatusCode() >> HttpStatus.INTERNAL_SERVER_ERROR

        long id=101
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(json, HttpStatus.OK)

        when:
        ProductInfo productInfo= productInfoService.getProductInfo(id)

        then:
        1 * restTemplate.getForEntity(*_ ) >> {throw httpStatusCodeException}
        thrown(ServiceException)

    }
    def "GetProductInfo empty response"() {
        setup:
        RestTemplate restTemplate = Mock(RestTemplate)
        ProductInfoService productInfoService= new ProductInfoService()
        productInfoService.restTemplateService=restTemplate

        String json =''
        long id=101
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(json, HttpStatus.OK)

        when:
        ProductInfo productInfo= productInfoService.getProductInfo(id)

        then:
        1 * restTemplate.getForEntity(*_ ) >> responseEntity
        thrown(ServiceException)

    }

}
