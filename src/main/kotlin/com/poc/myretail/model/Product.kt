package com.poc.myretail.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.io.Serializable

@Document("products")
data class Product(

	@Id
	val id : Long,

	@Field
	val name : String,

	@Field("current_price")
	@JsonProperty("current_price")
	val currentPrice : CurrentPrice

) : Serializable


