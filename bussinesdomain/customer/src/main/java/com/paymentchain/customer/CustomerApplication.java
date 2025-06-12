package com.paymentchain.customer;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

        
        //con este metodo en swagger le doy un nombre a mi api y una version      
  @Bean
  public OpenAPI springShopOpenAPI() {
      return new OpenAPI()
              .components(new Components())
              .info(new Info().title("Customer API")
               .version("1.0.0")        
                );
  }
}
