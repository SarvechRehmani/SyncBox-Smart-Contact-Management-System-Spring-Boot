package com.syncbox;

import com.syncbox.helper.AppConstants;
import com.syncbox.models.entities.User;
import com.syncbox.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.file.Paths;
import java.util.UUID;

@SpringBootApplication
public class SyncBoxApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder bCryptPasswordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(SyncBoxApplication.class, args);
		System.out.println("project started...");



	}

	@Override
	public void run(String... args) throws Exception {
//		String path = Paths.get("").toAbsolutePath().toString();
//		System.out.println(path);
	}
}
