package com.poc.myretail.client.redsky.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class ItemDomain(

	val tcin: String,

	@JsonProperty("product_description")
	val productDescription: ProductDescriptionDomain

)
