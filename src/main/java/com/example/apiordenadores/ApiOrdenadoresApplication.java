package com.example.apiordenadores;

import com.example.apiordenadores.entidades.Laptop;
import com.example.apiordenadores.repositorios.LaptopRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ApiOrdenadoresApplication {

	public static void main(String[] args) {

		ApplicationContext contex = SpringApplication.run(ApiOrdenadoresApplication.class, args);

		LaptopRepository repository = contex.getBean(LaptopRepository.class);

		Laptop laptop1 = new Laptop("HP",80000.00,"Intel i9",16);
		Laptop laptop2 = new Laptop("Lenovo",70900.00,"Ryzen 5",15);
		Laptop laptop3 = new Laptop("Asus",100000.00,"Intel i9",14);

		repository.save(laptop1);
		repository.save(laptop2);
		repository.save(laptop3);

	}

}
