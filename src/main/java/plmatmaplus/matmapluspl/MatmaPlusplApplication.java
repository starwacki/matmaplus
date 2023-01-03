package plmatmaplus.matmapluspl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import plmatmaplus.matmapluspl.entity.Comment;
import plmatmaplus.matmapluspl.repository.CommentRepository;
import plmatmaplus.matmapluspl.repository.CourseRepository;
import plmatmaplus.matmapluspl.repository.UserRepository;
import plmatmaplus.matmapluspl.service.UserLoginService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@SpringBootApplication
public class MatmaPlusplApplication {


	@Autowired
	CommentRepository commentRepository;

	@Autowired
	CourseRepository courseRepository;
	public static void main(String[] args) {
		SpringApplication.run(MatmaPlusplApplication.class, args);
		System.out.println();
	}

	@PostConstruct
	public void start() {
		System.out.println(commentRepository.findAllByCourseIdCoursesAndStarsIs(1l,4).size());
	}

}
