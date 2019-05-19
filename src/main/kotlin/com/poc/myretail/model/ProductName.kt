package com.poc.myretail.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class ProductName(

	@JsonProperty("name")
	var name: String?

) : Serializable
