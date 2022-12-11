package plmatmaplus.matmapluspl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plmatmaplus.matmapluspl.model.entity.Course;
import plmatmaplus.matmapluspl.model.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByCoursesContaining(Course course);
}
