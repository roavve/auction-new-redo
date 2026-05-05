package com.example.demo;

import com.example.demo.entity.Auction;
import com.example.demo.entity.User;
import com.example.demo.repository.AuctionRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(AuctionRepository auctionRepo,
								   UserRepository userRepo,
								   PasswordEncoder passwordEncoder) {
		return args -> {
			// Test admin user
			if (!userRepo.existsByEmail("admin@company.com")) {
				User admin = new User();
				admin.setFirstName("Admin");
				admin.setLastName("User");
				admin.setEmail("admin@company.com");
				admin.setPassword(passwordEncoder.encode("admin123"));
				admin.setRole("ADMIN");
				admin.setStatus(1);
				admin.setActive(true);
				admin.setLocked(false);
				admin.setRegisterDate(LocalDateTime.now());
				userRepo.save(admin);
				System.out.println(">>> ADMIN USER CREATED: admin@company.com / admin123");
			}

			// Test regular user
			if (!userRepo.existsByEmail("user@company.com")) {
				User user = new User();
				user.setFirstName("user");
				user.setLastName("test");
				user.setEmail("user@company.com");
				user.setPassword(passwordEncoder.encode("user123"));
				user.setRole("USER");
				user.setStatus(1);
				user.setActive(true);
				user.setLocked(false);
				user.setRegisterDate(LocalDateTime.now());
				userRepo.save(user);
				System.out.println(">>> TEST USER CREATED: user@company.com / user123");
			}

			// Test auction
			if (auctionRepo.count() == 0) {
				Auction a = new Auction();
				a.setName("auction test");
				a.setAuctionType("SELL");
				a.setStartBidValue(100.0);
				a.setCurrentHighestBid(100.0);
				a.setBidStep(10.0);
				a.setAdditionalMinute(5);
				a.setStatus("ACTIVE");
				a.setBidEndDate(LocalDateTime.now().plusDays(1));
				auctionRepo.save(a);
				System.out.println(">>> TEST AUCTION CREATED: ID " + a.getId());
			}
		};
	}
}