package plmatmaplus.matmapluspl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import plmatmaplus.matmapluspl.repository.UserRepository;
import plmatmaplus.matmapluspl.service.UserLoginService;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class MatmaPlusplApplication {


	@Autowired
	UserLoginService userLoginService;
	public static void main(String[] args) {
		SpringApplication.run(MatmaPlusplApplication.class, args);
		System.out.println();
	}

	@PostConstruct
	public void start() {
	}

}
