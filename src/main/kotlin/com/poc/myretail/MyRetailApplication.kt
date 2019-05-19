package com.poc.myretail

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate


@SpringBootApplication
class MyRetailApplication {

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			runApplication<MyRetailApplication>(*args)
		}
	}

	@Bean
	fun restTemplate(): RestTemplate {
		return RestTemplate()
	}

}
