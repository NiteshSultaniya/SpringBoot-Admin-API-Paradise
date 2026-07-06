package com.example.SpringBlogAdmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableAsync
public class SpringBlogAdminApplication {
//	private static PasswordEncoder passwordEncoder;
//	public  SpringBlogAdminApplication(PasswordEncoder passwordEncoder)
//	{
//		this.passwordEncoder=passwordEncoder;
//	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBlogAdminApplication.class, args);
		System.out.println("Project Run Succeffulyy");
//		String encryptedToken=passwordEncoder.encode("nitesh");
//		System.out.println(encryptedToken);
	}

}
