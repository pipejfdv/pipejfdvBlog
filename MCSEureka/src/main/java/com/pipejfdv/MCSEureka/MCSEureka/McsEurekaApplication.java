package com.pipejfdv.MCSEureka.MCSEureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class McsEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(McsEurekaApplication.class, args);
	}

}
