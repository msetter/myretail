package com.poc.myretail.service

import com.poc.myretail.DEFAULT_CURRENCY_AMOUNT
import com.poc.myretail.DEFAULT_CURRENCY_CODE
import com.poc.myretail.client.redsky.service.RedSkyService
import com.poc.myretail.exception.DocumentNotFoundException
import com.poc.myretail.model.CurrentPrice
import com.poc.myretail.model.Product
import com.poc.myretail.model.ProductDto
import com.poc.myretail.model.ProductPriceDto
import com.poc.myretail.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProductService(
	val productRepository: ProductRepository,
	val redSkyService: RedSkyService
) {

	private val logger = LoggerFactory.getLogger(javaClass)

	fun getProductName(productId: Long): String {

		return redSkyService.getProductName(productId)
	}

	fun getProductDetails(productId: Long): ProductDto? {

		val name = redSkyService.getProductName(productId)
		saveOrUpdate(productId, name)
		return getProductData(productId)
	}

	fun saveOrUpdate(id : Long, name : String) {
		val document = productRepository.findById(id)
		if(document.isEmpty) {
			val currentPrice = CurrentPrice(DEFAULT_CURRENCY_CODE, DEFAULT_CURRENCY_AMOUNT)
			val productDocument = Product(id, name, currentPrice)
			productRepository.save(productDocument)
		} else {
			 val d =document.get()
			 productRepository.save(Product(d.id, d.name, d.currentPrice))
		}
	}

	fun updateProductData(id: Long, updatedPrice: ProductPriceDto) : ProductDto {
		logger.info(">>> updateProductData : ${updatedPrice}")

		productRepository.findById(id).map {
			productRepository.save(Product(it.id, it.name, CurrentPrice(updatedPrice.currencyCode, updatedPrice.value)))
		}.orElseThrow { DocumentNotFoundException(id) }

		val updated = productRepository.findById(id)
		return(ProductDto(id, updated.get().name, ProductPriceDto(updated.get().currentPrice.price, updated.get().currentPrice.currencyCode)))
	}

	fun getProductData(id: Long): ProductDto {
		logger.info("getProductData - ID : ${id}")
		val document = (productRepository.findById(id)).get()
		return ProductDto(document.id, document.name,
			ProductPriceDto(document.currentPrice.price, document.currentPrice.currencyCode))

	}

}
