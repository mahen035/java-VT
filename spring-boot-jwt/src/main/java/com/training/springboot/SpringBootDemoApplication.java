package com.training.springboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication  // @Configuration + @ComponentScan + @EnableAutoConfiguration
public class SpringBootDemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootDemoApplication.class, args);
	}

}
