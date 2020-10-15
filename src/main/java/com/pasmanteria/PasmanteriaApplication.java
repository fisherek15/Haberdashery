package com.pasmanteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PasmanteriaApplication {

    private static final Logger log = LoggerFactory.getLogger(PasmanteriaApplication.class);

	public static void main(String[] args) {
		System.out.println("Hello");
		SpringApplication.run(PasmanteriaApplication.class, args);
	}
/*
	@Bean
	public CommandLineRunner loadData(ArticlesRep articlesInStockRepository){
		return (args) -> {

			// save a couple of customers
			articlesInStockRepository.save(new Articles("78541236", "AZf54", "Bauer", 10, "mb", 8.25f, 16.5f));
			articlesInStockRepository.save(new Articles("78521469", "BR56", "O'Brian", 10, "mb", 8.25f, 16.5f));
			articlesInStockRepository.save(new Articles("85214765", "34G3", "Bauer", 10, "mb", 8.25f, 16.5f));
			articlesInStockRepository.save(new Articles("75315964", "KJU876", "Palmer", 10, "mb", 8.25f, 16.5f));
			articlesInStockRepository.save(new Articles("75325965", "34As211", "Dessler", 10, "mb", 8.25f, 16.5f));


            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Articles articlesInStock : articlesInStockRepository.findAll()) {
                log.info(articlesInStock.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            Articles articlesInStock = articlesInStockRepository.findOne(1L);
            log.info("Customer found with findOne(1L):");
            log.info("--------------------------------");
            log.info(articlesInStock.toString());
            log.info("");

            // fetch customers by last name
            log.info("Customer found with findByArticleNameStartsWithIgnoreCase('Bauer'):");
            log.info("--------------------------------------------");
            for (Articles bauer : articlesInStockRepository
                    .findByCatalogNumberStartsWithIgnoreCase("Bauer")) {
                log.info(bauer.toString());
            }
            log.info("");
		};
	} */
}
