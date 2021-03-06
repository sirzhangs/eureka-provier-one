package com.sirzhangs.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
@MapperScan(value = "com.sirzhangs.provider.mapper")
public class EurekaProvierOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaProvierOneApplication.class, args);
	}

}
