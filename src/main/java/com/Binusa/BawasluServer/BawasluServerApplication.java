package com.Binusa.BawasluServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class BawasluServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(BawasluServerApplication.class, args);
		System.out.println("selesai");
	}
}

