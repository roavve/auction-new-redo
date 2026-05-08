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

import java.util.Date;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(AuctionRepository auctionRepo,
								   UserRepository userRepo,
								   PasswordEncoder passwordEncoder,
								   com.example.demo.repository.DictionaryItemRepository diRepo,
								   com.example.demo.repository.DictionaryRepository dictRepo) {
		return args -> {
			// ── Users ────────────────────────────────────────────────────────
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
				admin.setRegisterDate(new Date());
				userRepo.save(admin);
				System.out.println(">>> ADMIN USER CREATED: admin@company.com / admin123");
			}
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
				user.setRegisterDate(new Date());
				userRepo.save(user);
				System.out.println(">>> TEST USER CREATED: user@company.com / user123");
			}

			// ── Dictionary data (only if not already seeded) ─────────────────
			if (diRepo.findByKey("key.auctionType.buy").isEmpty()) {

				// Auction type dictionary
				com.example.demo.entity.Dictionary typeDict = new com.example.demo.entity.Dictionary();
				typeDict.setKey("auctionType");
				typeDict.setName("Auction Type");
				dictRepo.save(typeDict);

				com.example.demo.entity.DictionaryItem buyItem = new com.example.demo.entity.DictionaryItem();
				buyItem.setKey("key.auctionType.buy");
				buyItem.setName("BUY");
				buyItem.setNameGE("შეძენა");
				buyItem.setSortOrder(1);
				buyItem.setDictionary(typeDict);
				diRepo.save(buyItem);

				com.example.demo.entity.DictionaryItem sellItem = new com.example.demo.entity.DictionaryItem();
				sellItem.setKey("key.auctionType.sell");
				sellItem.setName("SELL");
				sellItem.setNameGE("გაყიდვა");
				sellItem.setSortOrder(2);
				sellItem.setDictionary(typeDict);
				diRepo.save(sellItem);

				// Auction status dictionary
				com.example.demo.entity.Dictionary statusDict = new com.example.demo.entity.Dictionary();
				statusDict.setKey("auctionStatus");
				statusDict.setName("Auction Status");
				dictRepo.save(statusDict);

				com.example.demo.entity.DictionaryItem activeStatus = new com.example.demo.entity.DictionaryItem();
				activeStatus.setKey("key.auctionStatus.active");
				activeStatus.setName("ACTIVE");
				activeStatus.setNameGE("აქტიური");
				activeStatus.setSortOrder(1);
				activeStatus.setDictionary(statusDict);
				diRepo.save(activeStatus);

				com.example.demo.entity.DictionaryItem completedStatus = new com.example.demo.entity.DictionaryItem();
				completedStatus.setKey("key.auctionStatus.completed");
				completedStatus.setName("COMPLETED");
				completedStatus.setNameGE("დასრულებული");
				completedStatus.setSortOrder(2);
				completedStatus.setDictionary(statusDict);
				diRepo.save(completedStatus);

				// Bid status dictionary
				com.example.demo.entity.Dictionary bidStatusDict = new com.example.demo.entity.Dictionary();
				bidStatusDict.setKey("bidStatus");
				bidStatusDict.setName("Bid Status");
				dictRepo.save(bidStatusDict);

				com.example.demo.entity.DictionaryItem bidActive = new com.example.demo.entity.DictionaryItem();
				bidActive.setKey("key.bid.active");
				bidActive.setName("ACTIVE");
				bidActive.setNameGE("აქტიური");
				bidActive.setSortOrder(1);
				bidActive.setDictionary(bidStatusDict);
				diRepo.save(bidActive);

				com.example.demo.entity.DictionaryItem bidStatusActive2 = new com.example.demo.entity.DictionaryItem();
				bidStatusActive2.setKey("key.bid.status.active");
				bidStatusActive2.setName("ACTIVE");
				bidStatusActive2.setNameGE("აქტიური");
				bidStatusActive2.setSortOrder(2);
				bidStatusActive2.setDictionary(bidStatusDict);
				diRepo.save(bidStatusActive2);

				// Invitation status dictionary
				com.example.demo.entity.Dictionary invStatusDict = new com.example.demo.entity.Dictionary();
				invStatusDict.setKey("invitationStatus");
				invStatusDict.setName("Invitation Status");
				dictRepo.save(invStatusDict);

				for (String[] kv : new String[][]{
						{"key.auctionInvitation.invited",  "INVITED",  "მოწვეული"},
						{"key.auctionInvitation.accepted", "ACCEPTED", "მიღებული"},
						{"key.auctionInvitation.rejected", "REJECTED", "უარყოფილი"},
						{"key.auctionInvitation.cancelled","CANCELLED","გაუქმებული"},
				}) {
					com.example.demo.entity.DictionaryItem it = new com.example.demo.entity.DictionaryItem();
					it.setKey(kv[0]); it.setName(kv[1]); it.setNameGE(kv[2]);
					it.setSortOrder(1); it.setDictionary(invStatusDict);
					diRepo.save(it);
				}

				// Comment status
				com.example.demo.entity.Dictionary commentDict = new com.example.demo.entity.Dictionary();
				commentDict.setKey("commentStatus");
				commentDict.setName("Comment Status");
				dictRepo.save(commentDict);

				com.example.demo.entity.DictionaryItem commentAnswered = new com.example.demo.entity.DictionaryItem();
				commentAnswered.setKey("key.coment.answered");
				commentAnswered.setName("ANSWERED");
				commentAnswered.setNameGE("პასუხგაცემული");
				commentAnswered.setSortOrder(1);
				commentAnswered.setDictionary(commentDict);
				diRepo.save(commentAnswered);

				com.example.demo.entity.DictionaryItem commentCancelled = new com.example.demo.entity.DictionaryItem();
				commentCancelled.setKey("key.coment.cancelled");
				commentCancelled.setName("CANCELLED");
				commentCancelled.setNameGE("გაუქმებული");
				commentCancelled.setSortOrder(2);
				commentCancelled.setDictionary(commentDict);
				diRepo.save(commentCancelled);

				System.out.println(">>> DICTIONARY DATA SEEDED");
			}
		};
	}
}