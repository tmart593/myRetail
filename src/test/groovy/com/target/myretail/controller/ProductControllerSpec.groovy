package com.target.myretail.controller

import com.target.myretail.exception.ProductNotFoundException
import com.target.myretail.model.CurrentPrice
import com.target.myretail.model.Product
import com.target.myretail.model.ProductInfo
import com.target.myretail.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class ProductControllerSpec extends Specification {
    def 'Get Product'() {
        setup:
        ProductController productController=new ProductController()

        ProductService productService = Mock(ProductService)
        productController.service = productService

        long id = 1000001

        String json = '''
                        {
                        "id": 1000001,
                        "name": "Godzilla (Blu-ray)",
                        "current_price": {
                            "value": 13.49,
                            "currency_code": "USD"
                        }                   
                      '''

        Product product1 = createProduct(id, "my product", 12.95, "USD")

        when:
        ResponseEntity<Product> responseEntity = productController.getProduct(id)


        then:
        1 * productService.getProduct(_ ) >> product1

        responseEntity.statusCode == HttpStatus.OK
        responseEntity.body.name.length()>0

    }

    def 'Put Product'() {
        setup:
        ProductController productController=new ProductController()

        ProductService productService = Mock(ProductService)
        productController.service = productService

        long id = 1000001

        Product product1 = createProduct(id, "Godzilla (Blu-ray)", 12.95, "USD")

        when:
        ResponseEntity<Product> responseEntity = productController.updateProductPrice(id, product1)

        then:
        1 * productService.updateProduct(*_ ) >> product1

        responseEntity.statusCode == HttpStatus.OK
        responseEntity.body.name.length()>0

    }

    def 'Put Product ProductNotFoundException'() {
        setup:
        ProductController productController=new ProductController()

        ProductService productService = Mock(ProductService)
        productController.service = productService

        long id = 0
        Product product1 = createProduct(id, "Godzilla (Blu-ray)", 12.95, "USD")

        when:
        ResponseEntity<Product> responseEntity = productController.updateProductPrice(id, product1)

        then:
        1 * productService.updateProduct(*_ ) >> {throw new ProductNotFoundException("test"){}}
        thrown(ProductNotFoundException)

    }


    Product createProduct( long id, String name, Double price, String currency){



        ProductInfo productInfo = new ProductInfo(id, name)

        CurrentPrice productPrice= new CurrentPrice(value: price, currencyCode: currency)

        return new Product(id: id, name: productInfo.name, currentPrice:  productPrice)


    }

}
