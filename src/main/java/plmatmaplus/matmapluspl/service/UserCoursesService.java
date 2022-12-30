package plmatmaplus.matmapluspl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plmatmaplus.matmapluspl.dto.UserCoursesDTO;
import plmatmaplus.matmapluspl.entity.Course;
import plmatmaplus.matmapluspl.repository.CourseRepository;
import plmatmaplus.matmapluspl.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserCoursesService {

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    @Autowired
    UserCoursesService(CourseRepository courseRepository,UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public List<UserCoursesDTO> mapToUserCoursesDTOList(HttpServletRequest request) {
     return getUserCourses(getUserId(request))
             .stream()
             .map(course ->
                     new UserCoursesDTO(course.getIdCourses(),
                                        course.getName(),
                                        course.getAdvancement(),
                                        course.getDetails().getImgLink(),
                                        course.getDetails().getLessons(),
                                        course.getDetails().getCourseVideosTime()))
             .toList();
    }

    private List<Course> getUserCourses(Long userID) {
        return userRepository.findByIdUsers(userID).get().getCourses();
    }

    private Long getUserId(HttpServletRequest request) {
        return Long.parseLong(request.getSession().getAttribute("user").toString());
    }
}
