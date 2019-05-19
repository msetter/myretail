package com.poc.myretail.controller

import com.poc.myretail.PARAM_ID
import com.poc.myretail.model.ProductDto
import com.poc.myretail.model.ProductName
import com.poc.myretail.model.ProductPriceDto
import com.poc.myretail.service.ProductService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
class ProductController(val productService: ProductService) {

	private val logger = LoggerFactory.getLogger(javaClass)

	@ApiOperation("Get product details for Product ID, TCIN or SKU")
	@ApiResponses(
		value = [ApiResponse(
			code = 200,
			message = "Successfully retrieved product from catalog"
		),
			ApiResponse(code = 404, message = "The product was not found in RedSky"),
			ApiResponse(
				code = 500,
				message = "The server was unable to process the request for an unknown reason"
			)]
	)
	@GetMapping("/product/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
	@ResponseBody
	fun getProductDetails(
		@ApiParam(name = PARAM_ID, value = "Product ID, TCIN or SKU", required = true, defaultValue = "13860428")
		@PathVariable("id") @NotBlank id: Long
	): ResponseEntity<ProductDto> {

		val productDto = productService.getProductDetails(id)

		return ResponseEntity(productDto, HttpStatus.OK)
	}

	@ApiOperation("Get product name for Product ID, TCIN or SKU. POC to display name/title returned from RedSky.")
	@GetMapping("/product", produces = [MediaType.APPLICATION_JSON_VALUE])
	@ResponseBody
	fun getProductName(
		@ApiParam(name = PARAM_ID, value = "Product ID, TCIN or SKU", required = true)
		@RequestParam(PARAM_ID, name = PARAM_ID, required = true) @NotBlank id: Long
	): ResponseEntity<ProductName> {

		logger.info("getProductName for $PARAM_ID: $id")

		val name = productService.getProductName(id)

		val productName = ProductName(name)

		return ResponseEntity(productName, HttpStatus.OK)
	}

	@ApiOperation(
		value = "Update product price",
		nickname = "putProduct",
		response = ProductDto::class,
		produces = "application/json"
	)
	@ApiResponses(
		value = [ApiResponse(
			code = 200,
			message = "Successfully updated product price"
		), ApiResponse(code = 304, message = "Unable to modify product price"), ApiResponse(
			code = 500,
			message = "There has been an unexpected error"
		)]
	)
	@PutMapping("/product/{id}")
	@ResponseBody // we include values in the response body
	fun updateProduct(
		@ApiParam(
			name = "id",
			value = "The product identifier or SKU for the product being queried",
			required = true,
			defaultValue = "13860428"
		)
		@PathVariable id: Long,
		@ApiParam(name = "product", value = "The JSON data being used to update the product", required = true)
		@Valid @RequestBody updatedPrice: ProductPriceDto
	): ResponseEntity<ProductDto> {

		return ResponseEntity(productService.updateProductData(id, updatedPrice), HttpStatus.OK)
	}

}
