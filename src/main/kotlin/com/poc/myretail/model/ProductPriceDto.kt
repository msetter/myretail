package com.poc.myretail.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import javax.validation.constraints.NotNull

data class ProductPriceDto (

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	val value : BigDecimal,

    @NotNull
    @JsonProperty("currency_code")
    val currencyCode : String

)
