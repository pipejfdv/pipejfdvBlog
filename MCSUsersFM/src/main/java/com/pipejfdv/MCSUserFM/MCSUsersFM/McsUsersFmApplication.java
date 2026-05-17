package com.pipejfdv.MCSUserFM.MCSUsersFM;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
* Main entry point for the MCSUsersFM microservice
* Registers with Eureka discovery service and starts Spring Boot application
*/
@EnableDiscoveryClient
@SpringBootApplication
public class McsUsersFmApplication {

	/*
	* Starts the MCSUsersFM Spring Boot application
	* @Params args Command line arguments
	*/
	public static void main(String[] args) {
		SpringApplication.run(McsUsersFmApplication.class, args);
	}

}
