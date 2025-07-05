package com.pipejfdv.MCSConfig.MCSConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class McsConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(McsConfigApplication.class, args);
	}

}
