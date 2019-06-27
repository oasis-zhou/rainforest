package rf.sp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SalesPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesPlatformApplication.class, args);
	}
}
