package plmatmaplus.matmapluspl.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plmatmaplus.matmapluspl.entity.Course;
import plmatmaplus.matmapluspl.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    List<Course> findAllByPriceIsLessThan(double price);

    List<Course> findAllByAdvancementIs(String advancement);

    Optional<Course> findByIdCourses(Long idCourses);

    List<Course> findAllByUserEntities(UserEntity userEntity);

}
