package com.poc.myretail.repository

import com.poc.myretail.model.Product
import org.springframework.data.repository.CrudRepository

interface ProductRepository : CrudRepository<Product, Long> {

}
