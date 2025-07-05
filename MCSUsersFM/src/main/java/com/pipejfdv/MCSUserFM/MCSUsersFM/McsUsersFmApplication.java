package com.pipejfdv.MCSUserFM.MCSUsersFM;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class McsUsersFmApplication {

	public static void main(String[] args) {
		SpringApplication.run(McsUsersFmApplication.class, args);
	}

}
