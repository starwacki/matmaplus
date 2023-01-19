package plmatmaplus.matmapluspl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import plmatmaplus.matmapluspl.dto.UserCoursesDTO;
import plmatmaplus.matmapluspl.entity.Course;
import plmatmaplus.matmapluspl.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserCoursesService {


    private final UserRepository userRepository;

    public boolean isNoActiveSession(HttpServletRequest request) {
        return request.getSession().getAttribute("user")==null;
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

    private Set<Course> getUserCourses(Long userID) {
        return userRepository.findByIdUsers(userID).get().getCourses();
    }

    private Long getUserId(HttpServletRequest request) {
        return Long.parseLong(request.getSession().getAttribute("user").toString());
    }


}
