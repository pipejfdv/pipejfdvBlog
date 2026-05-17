package com.pipejfdv.MCSConfig.MCSConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
/*
* Main entry point for the MCSConfig centralized configuration server
* Serves external configuration for all microservices using Spring Cloud Config
*/
public class McsConfigApplication {

	/*
	* Starts the Spring Boot application with the given arguments
	* @Param args command-line arguments passed at startup
	* @Return void
	* @Throw none
	*/
	public static void main(String[] args) {
		SpringApplication.run(McsConfigApplication.class, args);
	}

}
