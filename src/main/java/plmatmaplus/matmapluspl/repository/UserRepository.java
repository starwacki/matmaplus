package plmatmaplus.matmapluspl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plmatmaplus.matmapluspl.entity.Course;
import plmatmaplus.matmapluspl.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findAllByCoursesContaining(Course course);

    Optional<UserEntity> findUserByUsernameAndEmail(String username, String email);

    Optional<UserEntity> findUserByUsername(String username);

    Optional<UserEntity> findUserByEmail(String email);

    Optional<UserEntity> findByIdUsers(Long idUsers);




}
