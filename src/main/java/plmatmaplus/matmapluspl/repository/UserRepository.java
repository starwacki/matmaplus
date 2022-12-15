package plmatmaplus.matmapluspl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plmatmaplus.matmapluspl.entity.Course;
import plmatmaplus.matmapluspl.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByCoursesContaining(Course course);

    Optional<User> findUserByUsernameAndEmail(String username, String email);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);


}
