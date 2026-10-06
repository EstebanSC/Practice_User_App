package com.esc.pro.users_app;

import com.esc.pro.users_app.Repositories.UserRepository;
import com.esc.pro.users_app.entities.User;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UsersAppApplication implements ApplicationRunner {

	@Autowired
	private Faker faker;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(UsersAppApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		for(int i = 0; i < 100000; i++){
			User user = new User();
			user.setUserName(faker.name().username());
			user.setPassword(faker.hobbit().character());
			userRepository.save(user);
		}
	}
}
