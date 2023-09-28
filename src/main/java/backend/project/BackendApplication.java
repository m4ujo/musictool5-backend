package backend.project;

import backend.project.entities.*;
import backend.project.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner mappingDemo(
			AuthorityRepository authorityRepository,
			PasswordEncoder passwordEncoder
	) {
		return args -> {
			authorityRepository.save(new Authority(AuthorityName.ROLE_ADMIN));
			authorityRepository.save(new Authority(AuthorityName.ROLE_STUDENT));
			authorityRepository.save(new Authority(AuthorityName.ROLE_PRINCIPAL));
			authorityRepository.save(new Authority(AuthorityName.ROLE_TEACHER));

			System.out.println(passwordEncoder.encode("password"));
		};
	}

}
