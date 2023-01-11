package meong.nyang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NyangApplication {

	public static void main(String[] args) {
		SpringApplication.run(NyangApplication.class, args);
	}

}
