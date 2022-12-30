package plmatmaplus.matmapluspl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import plmatmaplus.matmapluspl.repository.CourseRepository;
import plmatmaplus.matmapluspl.repository.UserRepository;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class MatmaPlusplApplication {

@Autowired
	CourseRepository courseRepository;
@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(MatmaPlusplApplication.class, args);
	}

}
