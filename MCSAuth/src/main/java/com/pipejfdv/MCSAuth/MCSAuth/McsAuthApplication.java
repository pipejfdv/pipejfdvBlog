package com.pipejfdv.MCSAuth.MCSAuth;

import com.pipejfdv.MCSAuth.MCSAuth.Components.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient // recognize service in Eureka
@SpringBootApplication //indentify what a spring application is
@EnableConfigurationProperties(JwtProperties.class) // create class with information properties in MCSConfig jwt
@EnableFeignClients // this identify allows microservice information to be consumed
public class McsAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(McsAuthApplication.class, args);
	}

}
