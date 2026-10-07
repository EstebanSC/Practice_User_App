package com.esc.pro.users_app;

import com.esc.pro.users_app.Repositories.RoleRepository;
import com.esc.pro.users_app.Repositories.UserInRoleRepository;
import com.esc.pro.users_app.Repositories.UserRepository;
import com.esc.pro.users_app.entities.Role;
import com.esc.pro.users_app.entities.User;
import com.esc.pro.users_app.entities.UserInRole;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Random;

@SpringBootApplication
public class UsersAppApplication implements ApplicationRunner {

	@Autowired
	private Faker faker;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserInRoleRepository userInRoleRepository;

	public static void main(String[] args) {
		SpringApplication.run(UsersAppApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Role roles[] = {new Role("ADMIN"),new Role("SUPPORT"),new Role("USER")};
		roleRepository.saveAll(Arrays.asList(roles));

		for(int i = 0; i < 100000; i++){
			User user = new User();
			user.setUserName(faker.name().username());
			user.setPassword(faker.hobbit().character());
			userRepository.save(user);

			UserInRole userInRole = new UserInRole();
			userInRole.setUser(user);
			userInRole.setRole(roles[new Random().nextInt(3)]);
			userInRoleRepository.save(userInRole);
		}
	}
}
