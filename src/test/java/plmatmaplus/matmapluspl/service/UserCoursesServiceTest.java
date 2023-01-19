package plmatmaplus.matmapluspl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import plmatmaplus.matmapluspl.dto.UserCoursesDTO;
import plmatmaplus.matmapluspl.entity.Course;
import plmatmaplus.matmapluspl.entity.CourseDetails;
import plmatmaplus.matmapluspl.entity.UserEntity;
import plmatmaplus.matmapluspl.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserCoursesServiceTest {

    private UserRepository userRepository;
    private UserCoursesService userCoursesService;

    @BeforeEach
    public   void initialize() {
        userRepository = mock(UserRepository.class);
        userCoursesService = new UserCoursesService(userRepository);
    }

    @Test
    void isNoActiveSession_whenSessionIsActive_shouldReturnFalse() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";
        int idUser = 1;
        session.setAttribute(attributeUser,idUser);
        given(request.getSession()).willReturn(session);
        given(request.getSession().getAttribute(attributeUser)).willReturn(idUser);


        //when
        boolean isNoActiveSession = userCoursesService.isNoActiveSession(request);

        //then
        Assertions.assertFalse(isNoActiveSession);
    }

    @Test
    void  isNoActiveSession_whenSessionIsNoActive_shouldReturnTrue() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";
        given(request.getSession()).willReturn(session);
        given(request.getSession().getAttribute(attributeUser)).willReturn(null);


        //when
        boolean isNoActiveSession = userCoursesService.isNoActiveSession(request);

        //then
        Assertions.assertTrue(isNoActiveSession);
    }

    @Test
    void mapToUserCoursesDTOList_whenUserCoursesIsEmpty_shouldReturnEmptyList() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";
        UserEntity userEntity = new UserEntity("username","password","email");
        Set<Course> userCourses = new HashSet<>();
        userEntity.setCourses(userCourses);
        int idUser = 1;
        session.setAttribute(attributeUser,idUser);
        given(request.getSession()).willReturn(session);
        given(request.getSession().getAttribute(attributeUser)).willReturn(idUser);
        given(userRepository.findByIdUsers((long) idUser)).willReturn(Optional.of(userEntity));

        //then
        List<UserCoursesDTO> userCoursesDTOList = userCoursesService.mapToUserCoursesDTOList(request);
        int sizeOfList = userCoursesDTOList.size();
        int expectedSizeOfList  = 0;
        Assertions.assertEquals(expectedSizeOfList,sizeOfList);
    }

    @Test
    void mapToUserCoursesDTOList_whenUserHaveOneCourses_shouldReturnListWithOneElement() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";
        UserEntity userEntity = new UserEntity("username","password","email");
        Course course = new Course();
        course.setDetails(mock(CourseDetails.class));
        Set<Course> userCourses = new HashSet<>();
        userCourses.add(course);
        userEntity.setCourses(userCourses);
        int idUser = 1;
        session.setAttribute(attributeUser,idUser);
        given(request.getSession()).willReturn(session);
        given(request.getSession().getAttribute(attributeUser)).willReturn(idUser);
        given(userRepository.findByIdUsers((long) idUser)).willReturn(Optional.of(userEntity));

        //then
        List<UserCoursesDTO> userCoursesDTOList = userCoursesService.mapToUserCoursesDTOList(request);
        int sizeOfList = userCoursesDTOList.size();
        int expectedSizeOfList  = 1;
        Assertions.assertEquals(expectedSizeOfList,sizeOfList);
    }
}