package com.poc.myretail.config

import com.mongodb.MongoClient
import com.poc.myretail.DB_HOST
import com.poc.myretail.DB_NAME
import com.poc.myretail.DB_PORT
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoConfiguration


@Configuration
class MongoConfig : AbstractMongoConfiguration() {

	override fun getDatabaseName(): String {
		return DB_NAME
	}

	override fun mongoClient(): MongoClient {
		return MongoClient(DB_HOST, DB_PORT)
	}

}
