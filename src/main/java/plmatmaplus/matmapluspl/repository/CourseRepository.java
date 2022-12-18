package plmatmaplus.matmapluspl.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plmatmaplus.matmapluspl.entity.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    List<Course> findAllByPriceIsLessThan(double price);

    List<Course> findAllByAdvancementIs(String advancement);

}
