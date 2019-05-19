package com.poc.myretail.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ProductDto(

	val id: Long,

	val name : String?,

	@JsonProperty("current_price")
	val productPrice: ProductPriceDto

)
