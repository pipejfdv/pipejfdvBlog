package com.pipejfdv.Juegos.MCSJuegos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*
* Main entry point for the MCSJuegos microservice
* Enables service discovery and Feign clients for inter-service communication
*/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class McsJuegosApplication {

	/*
	* Starts the Spring Boot application
	* @Param args command-line arguments
	*/
	public static void main(String[] args) {
		SpringApplication.run(McsJuegosApplication.class, args);
	}

}
