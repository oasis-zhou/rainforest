package rf.saleshorizon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "rf")
@EnableJpaRepositories(basePackages = "rf")
@EntityScan(basePackages = "rf")
@EnableCaching
public class SalesHorizonApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesHorizonApplication.class, args);
	}
}
