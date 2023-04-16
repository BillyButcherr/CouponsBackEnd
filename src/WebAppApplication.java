package com.webApp;

import com.webApp.repositories.CompanyRepository;
import com.webApp.repositories.CouponRepository;
import com.webApp.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WebAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebAppApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(CompanyRepository companyRepository, CustomerRepository customerRepository, CouponRepository couponRepository, PasswordEncoder passwordEncoder){
		return args -> {
//			customerRepository.save(new Customer("billy", "boy", "billy@boy.com", "{bcrypt}"+passwordEncoder.encode("1234")));
//			Company company = new Company("Gal-Corp", "gal@galcorp.com", "{bcrypt}"+passwordEncoder.encode("1234"));
//			companyRepository.save(company);
//
//			couponRepository.save(new Coupon(company, Category.Vacation, "קולומביה הבריטית, קנדה", "lorem ipsum", "2023-04-08", "2023-05-08", 15, 2200, "url"));
//			couponRepository.save(new Coupon(company, Category.Vacation, "קייפטאון, דרום אפריקה", "lorem ipsum", "2023-04-08", "2023-05-08", 15, 2200, "url"));
//			couponRepository.save(new Coupon(company, Category.Vacation, "ברן, שוויץ", "lorem ipsum", "2023-04-08", "2023-05-08", 15, 2200, "url"));
//			couponRepository.save(new Coupon(company, Category.Vacation, "שיזואוקה, יפן", "lorem ipsum", "2023-04-08", "2023-05-08", 15, 2200, "url"));
//			couponRepository.save(new Coupon(company, Category.Vacation, "וושינגטון, ארצות הברית", "lorem ipsum", "2023-04-08", "2023-05-08", 15, 2200, "url"));
//			couponRepository.save(new Coupon(company, Category.Vacation, "הרי הטטרה, סלובקיה", "lorem ipsum", "2023-04-08", "2023-05-08", 15, 2200, "url"));
//			couponRepository.save(new Coupon(company, Category.Vacation, "גליציה, ספרד", "lorem ipsum", "2023-04-08", "2023-05-08", 15, 2200, "url"));
//			couponRepository.save(new Coupon(company, Category.Vacation, "מפלי איגואסו, ארגנטינה", "lorem ipsum", "2023-04-08", "2023-05-08", 15, 2200, "url"));

		};
	}
}
