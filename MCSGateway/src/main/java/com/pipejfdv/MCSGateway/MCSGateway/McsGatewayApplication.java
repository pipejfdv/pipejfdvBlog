package com.pipejfdv.MCSGateway.MCSGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* Main entry point for the MCSGateway microservice.
* Sets up the Spring Boot application and starts the API Gateway.
*/
@SpringBootApplication
public class McsGatewayApplication {

	/*
	* Launches the MCSGateway application using Spring Boot.
	* @Param args command-line arguments passed at startup
	* @Return void
	*/
	public static void main(String[] args) {
		SpringApplication.run(McsGatewayApplication.class, args);
	}

}
