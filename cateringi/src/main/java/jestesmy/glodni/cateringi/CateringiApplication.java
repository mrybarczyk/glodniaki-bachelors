package jestesmy.glodni.cateringi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("jestesmy.glodni.cateringi.repository")
@EntityScan("jestesmy.glodni.cateringi.domain.model")
@SpringBootApplication
public class CateringiApplication {


	public static void main(String[] args) {
		SpringApplication.run(CateringiApplication.class, args);
	}


}
