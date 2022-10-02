package com.example.msmovimiento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsMovimientoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsMovimientoApplication.class, args);
	}

}
