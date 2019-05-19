package com.poc.myretail.client.redsky.service

import com.poc.myretail.client.redsky.domain.ProductResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class RedSkyService(val restTemplate: RestTemplate) {

	@Value("\${client.redsky.rest.endpoint}")
	var restEndpoint: String = ""

	fun getProductName(productId: Long): String {
		val url = String.format(restEndpoint, productId)

		val productResponse = restTemplate.getForObject(url, ProductResponse::class.java)
		return productResponse!!.product.item.productDescription.title
	}

}
