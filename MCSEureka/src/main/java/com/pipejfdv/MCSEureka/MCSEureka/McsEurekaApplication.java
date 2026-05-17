package com.pipejfdv.MCSEureka.MCSEureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
/*
* Main entry point for the MCSEureka service discovery server
* Runs a Netflix Eureka server where all microservices register themselves
*/
public class McsEurekaApplication {

	/*
	* Starts the Spring Boot application with the given arguments
	* @Param args command-line arguments passed at startup
	* @Return void
	* @Throw none
	*/
	public static void main(String[] args) {
		SpringApplication.run(McsEurekaApplication.class, args);
	}

}
