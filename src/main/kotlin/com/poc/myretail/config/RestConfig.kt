package com.poc.myretail.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class RestConfig {
	@Bean
	fun corsFilter(): CorsFilter {
		val source = UrlBasedCorsConfigurationSource()
		val config = CorsConfiguration()
		config.allowCredentials = true
		config.addAllowedOrigin("*")
		config.addAllowedHeader("*")
		config.addAllowedMethod("OPTIONS")
		config.addAllowedMethod("GET")
		config.addAllowedMethod("POST")
		config.addAllowedMethod("PUT")
		config.addAllowedMethod("DELETE")
		source.registerCorsConfiguration("/**", config)
		return CorsFilter(source)
	}
}
