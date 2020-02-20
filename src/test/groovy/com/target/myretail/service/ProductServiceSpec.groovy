package com.target.myretail.service

import com.target.myretail.model.CurrencyCode
import com.target.myretail.model.CurrentPrice
import com.target.myretail.model.Product
import com.target.myretail.model.ProductInfo
import com.target.myretail.model.ProductPrice
import com.target.myretail.repository.ProductPriceRepository
import spock.lang.Specification

class ProductServiceSpec extends Specification {
    def "GetProduct"() {
        setup:

        ProductPriceRepository priceRepository=Mock(ProductPriceRepository);

        ProductInfoService productInfoService=Mock(ProductInfoService);

        ProductPriceService productPriceService=Mock(ProductPriceService);

        long id=101
        String name='test'

        ProductInfo productInfo= new ProductInfo(id, name);
        ProductPrice productPrice = new ProductPrice();
        productPrice.value =22.95
        productPrice.currencyCode= CurrencyCode.USD

        ProductService productService = new ProductService(priceRepository, productInfoService,productPriceService )

        when:
        Product product= productService.getProduct(id)

        then:
        1 * productInfoService.getProductInfo(_ ) >> productInfo
        1 * productPriceService.getProductPrice(_ ) >> productPrice
        product.name==name
        product.getCurrentPrice().value==22.95



    }

    def "Update Product Price"() {


        ProductPriceRepository priceRepository=Mock(ProductPriceRepository);

        ProductInfoService productInfoService=Mock(ProductInfoService);

        ProductPriceService productPriceService=Mock(ProductPriceService);

        long id=101
        String name='test'

        ProductInfo productInfo= new ProductInfo(id, name);
        ProductPrice productPrice = new ProductPrice();
        productPrice.value =22.95
        productPrice.currencyCode= CurrencyCode.USD

        CurrentPrice currentPrice = new CurrentPrice(value: 22.95, currencyCode: CurrencyCode.USD )

        Product productInput= new Product(id: 101, name: name, currentPrice: currentPrice)

        ProductService productService = new ProductService(priceRepository, productInfoService,productPriceService )

        when:
        Product product= productService.updateProduct(id, productInput)

        then:
        1 * productInfoService.getProductInfo(_ ) >> productInfo
        1 * productPriceService.getProductPrice(_ ) >> productPrice
        1 * priceRepository.save(_ )
        product.name==name
        product.getCurrentPrice().value==22.95



    }



}
