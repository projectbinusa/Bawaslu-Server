package com.Binusa.BawasluServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class BawasluApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}


	@Bean
	public DataSource getDataSource() {
		return DataSourceBuilder.create()
				.driverClassName("com.mysql.cj.jdbc.Driver")
				.url("jdbc:mysql://localhost:3306/bawaslu")
				.username("root")
				.password("12345678")
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(BawasluApplication.class, args);
		System.out.println("selesaii");
	}}
