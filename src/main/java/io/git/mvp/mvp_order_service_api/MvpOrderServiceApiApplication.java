package io.git.mvp.mvp_order_service_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableJpaAuditing
@EnableKafka
public class MvpOrderServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvpOrderServiceApiApplication.class, args);
	}

}
