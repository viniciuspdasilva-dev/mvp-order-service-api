package io.git.mvp.mvp_order_service_api;

import org.springframework.boot.SpringApplication;

public class TestMvpOrderServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(MvpOrderServiceApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
