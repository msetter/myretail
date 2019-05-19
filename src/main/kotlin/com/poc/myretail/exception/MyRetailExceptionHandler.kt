package com.poc.myretail.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class MyRetailExceptionHandler : DefaultHandlerExceptionResolver() {

	@ExceptionHandler(DocumentNotFoundException::class)
	fun handleDocumentNotFoundException(ex: DocumentNotFoundException): ResponseEntity<ExceptionInfo> {
		logger.info(">>>> inside handleDocumentNotFoundException")

		return ResponseEntity<ExceptionInfo>(
			ExceptionInfo(HttpStatus.NOT_FOUND, ex.message!!),
			HttpStatus.NOT_FOUND
		)
	}


	@ExceptionHandler(HttpClientErrorException::class)
	fun handleHttpClientException(
		ex: HttpStatusCodeException,
		request: HttpServletRequest,
		response: HttpServletResponse,
		handler: Any
	): ResponseEntity<ExceptionInfo> {
		logger.info(">>>> inside handleHttpClientException")
		return ResponseEntity<ExceptionInfo>(
			ExceptionInfo(ex.statusCode, ex.message!!),
			ex.statusCode
		)
	}

	@ExceptionHandler(Exception::class)
	fun handleUncaughtExceptions(ex: Exception): ResponseEntity<ExceptionInfo> {

		return ResponseEntity<ExceptionInfo>(
			ExceptionInfo(HttpStatus.INTERNAL_SERVER_ERROR, ex.message!!),
			HttpStatus.INTERNAL_SERVER_ERROR
		)
	}
}
