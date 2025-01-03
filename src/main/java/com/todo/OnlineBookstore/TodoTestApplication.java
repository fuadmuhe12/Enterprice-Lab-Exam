// Fuad Mohammed Obsa

package com.todo.OnlineBookstore;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@ServletComponentScan 
@ImportResource("classpath:applicationContext.xml") 
public class TodoTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoTestApplication.class, args);
	}
}
