package com.poc.myretail.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class ProductData(

	@JsonProperty("id")
	val productId: Long,

	@JsonProperty("name")
	var name: String?,

	@JsonProperty("current_price")
	var currentPrice: CurrentPrice?

) : Serializable
