package com.pipejfdv.MCSAuth.MCSAuth;

import com.pipejfdv.MCSAuth.MCSAuth.Components.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*
* Main entry point for the MCSAuth microservice.
* Handles authentication, JWT token creation, and user session management.
*/
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
@EnableFeignClients
public class McsAuthApplication {

	/*
	* Starts the Spring Boot application
	* @Param args String[] command line arguments
	* @Return void
	*/
	public static void main(String[] args) {
		SpringApplication.run(McsAuthApplication.class, args);
	}

}
