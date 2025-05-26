package com.santosh.bankingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BankingSytemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingSytemApplication.class, args);
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("pass123"));
	}

}
