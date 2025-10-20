package com.login_auth_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class LoginAuthApiApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
                .directory("./") // Garante que ele procure o .env na raiz do projeto
                .ignoreIfMissing() // Não falha se o arquivo .env não existir
                .load();
		SpringApplication.run(LoginAuthApiApplication.class, args);
	}
	

}
