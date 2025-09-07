package com.pipejfdv.MCSAuth.MCSAuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class McsAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(McsAuthApplication.class, args);
	}

}
