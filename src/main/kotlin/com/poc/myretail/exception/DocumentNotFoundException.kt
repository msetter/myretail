package com.poc.myretail.exception

class DocumentNotFoundException(id: Long) : RuntimeException(
	"No document found with ID of $id"
)

