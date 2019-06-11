package com.synopsys.integration.create.apigen

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application {
	public static final String CURRENT_API_SPECIFICATION = '/api-specification/2019.6.0'
	public static final String RESPONSE_TOKEN = 'Id' + File.separator + 'GET' + File.separator

	static void main(String[] args) {
		SpringApplication.run(Application, args)
	}

}
