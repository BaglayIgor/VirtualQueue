package com.baglie.VirtualQueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class VirtualQueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualQueueApplication.class, args);
	}

}
