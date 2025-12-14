package org.esprim.TpFoyer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = "org.esprim")
public class TpFoyerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TpFoyerApplication.class, args);
	}

}
