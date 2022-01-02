package com.clsoftware.loja_virtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LojaVirtualApplication {

	public static void main(String[] args) {
		SpringApplication.run(LojaVirtualApplication.class, args);
		
		//criptografar senha //senha 1234= $2a$10$3NGK7uhoi2cdihfCJ.jC9.vDqP9hHA/lXXeLZaXooijgG/aM4K4HS
		//BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//String result= encoder.encode("1234");
		//System.out.println(result); 
	}

}
