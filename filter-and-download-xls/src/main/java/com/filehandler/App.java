package com.filehandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.example.demo"})
@ComponentScan(basePackageClasses = { FileController.class, UploadController.class })
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
