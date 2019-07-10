package rf.cohorizon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "rf")
@EnableJpaRepositories(basePackages = "rf")
@EntityScan(basePackages = "rf")
@EnableCaching
@EnableScheduling
public class CollaborationHorizonApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollaborationHorizonApplication.class, args);
	}
}
