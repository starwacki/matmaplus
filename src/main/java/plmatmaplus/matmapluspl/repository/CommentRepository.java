package plmatmaplus.matmapluspl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plmatmaplus.matmapluspl.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAllByCourseIdCourses(long courseId);

    List<Comment> findAllByCourseIdCoursesAndStarsIs(long courseId, int stars);


}
