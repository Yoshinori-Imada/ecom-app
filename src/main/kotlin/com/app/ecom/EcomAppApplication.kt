package com.app.ecom

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class EcomAppApplication

fun main(args: Array<String>) {
	runApplication<EcomAppApplication>(*args)

}
