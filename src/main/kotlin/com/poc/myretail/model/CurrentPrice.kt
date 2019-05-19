package com.poc.myretail.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

class CurrentPrice(

	@JsonProperty("currency_code")
	val currencyCode : String,

	@JsonProperty("value")
	val price : BigDecimal

)
