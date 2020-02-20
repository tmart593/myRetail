package com.target.myretail.service

import com.target.myretail.model.ProductInfo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
        String url ='https://redsky.net'
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(json, HttpStatus.OK)

        when:
        ProductInfo productInfo= productInfoService.getProductInfo(id)

        then:
        1 * restTemplate.getForEntity(*_ ) >> responseEntity
        productInfo.name=='test'

    }
}
