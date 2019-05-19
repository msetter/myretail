package com.poc.myretail.exception

import org.springframework.http.HttpStatus
import java.io.Serializable

data class ExceptionInfo (

	val httpStatus : HttpStatus,
	val message : String

) : Serializable
