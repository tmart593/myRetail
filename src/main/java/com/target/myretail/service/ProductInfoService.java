package com.target.myretail.service;

import com.jayway.jsonpath.JsonPath;
import com.target.myretail.exception.ProductNotFoundException;
import com.target.myretail.exception.ServiceException;
import com.target.myretail.model.ProductInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductInfoService.class);

    private RestTemplate restTemplate;

    @Autowired
    RestTemplate restTemplateService;

    public ProductInfoService(){}

    public ProductInfo getProductInfo(long id) {
        String url = buildUrl(id);
        ResponseEntity<String> result = null;
        try {
            result = restTemplateService.getForEntity(url, String.class);
        } catch (HttpStatusCodeException h) {
            String errMsg = "could not retrieve product info for id: "+id;
            LOGGER.error(errMsg+ " error: " + h.getMessage());
            if (h.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                throw new ProductNotFoundException(errMsg);
            } else {
                throw new ServiceException(errMsg);
            }
        }

        if (result!=null && HttpStatus.OK.equals(result.getStatusCode()) && !StringUtils.isEmpty(result.getBody())) {
            String name = JsonPath.read(result.getBody(), "$.product.item.product_description.title");
            return new ProductInfo(id, name);
        } else {
            throw new ServiceException("could not retrieve product info for id: " + id);
        }
    }
    private String buildUrl(long id) {
        return "https://redsky.target.com/v2/pdp/tcin/" + id +
                "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";
    }

}
