package com.poc.myretail.controller

import com.poc.myretail.MyRetailApplicationSpec
import com.poc.myretail.client.redsky.service.RedSkyService
import com.poc.myretail.model.CurrentPrice
import com.poc.myretail.model.Product
import com.poc.myretail.model.ProductDto
import com.poc.myretail.model.ProductPriceDto
import com.poc.myretail.repository.ProductRepository
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate

class ProductControllerIT extends MyRetailApplicationSpec {

    private static final String CURRENCY_CODE = "USD"
    private static final String CURRENCY_CODE_NEW = "ZZZ"
    private static final Long PRODUCT_ID = 1234567890
    private static final String PRODUCT_TITLE = "The Bestest"
    private static final BigDecimal PRODUCT_PRICE = 25.41
    private static final BigDecimal PRODUCT_PRICE_NEW = 131.98
    private static final Product PRODUCT = new Product(
            PRODUCT_ID,
            PRODUCT_TITLE,
            new CurrentPrice(CURRENCY_CODE, PRODUCT_PRICE)
    )

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    ProductRepository productRepository

    @SpringBean
    RedSkyService redSkyServiceMock = Mock()

    def 'Retrieve hydrated product/document'() {
        given: "Current document with ID $PRODUCT_ID"
        productRepository.save(PRODUCT)

        and: "Product title/name of $PRODUCT_TITLE from the RedSky service"
        redSkyServiceMock.getProductName(_ as Long) >> {
            return PRODUCT_TITLE
        }

        when: "Request product with ID PRODUCT_ID"
        def response = restTemplate.getForEntity("/product/{id}", ProductDto, PRODUCT_ID)

        then: 'Return product name with the HTTP 200 success status code'
        response.statusCodeValue == 200
        with(response.body) {
            it.name == PRODUCT_TITLE
        }
    }

    def 'Update '() {
        given: "Current product/document with price $PRODUCT_PRICE and currencyCode $CURRENCY_CODE"
        productRepository.save(PRODUCT)

        when:
        restTemplate.put("/product/{id}", new ProductPriceDto(PRODUCT_PRICE_NEW, CURRENCY_CODE_NEW), PRODUCT_ID)

        then:
        noExceptionThrown()

        and: "Product database has updated values with price $PRODUCT_PRICE_NEW and currencyCode $CURRENCY_CODE_NEW"
        def productDocument = productRepository.findById(PRODUCT_ID).get()
        with(productDocument) {
            currentPrice.price == PRODUCT_PRICE_NEW
            currentPrice.currencyCode == CURRENCY_CODE_NEW
        }
    }

    def 'Throw IllegalArgumentException when ProductPriceDto is missing values'() {
        given: "Current product/document with price $PRODUCT_PRICE and currencyCode $CURRENCY_CODE"
        productRepository.save(PRODUCT)

        when: 'Invalid request sent'
        restTemplate.put("/product/{id}", new ProductPriceDto(null, null), PRODUCT_ID)

        then: 'Document in database does not change'
        thrown(IllegalArgumentException)

    }

    def cleanup() {
        productRepository.deleteAll()
    }

}
