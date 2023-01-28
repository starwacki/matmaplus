package plmatmaplus.matmapluspl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plmatmaplus.matmapluspl.controller.CourseID;
import plmatmaplus.matmapluspl.dto.CourseCommentDTO;
import plmatmaplus.matmapluspl.dto.CourseOpinionSectionDTO;
import plmatmaplus.matmapluspl.entity.Comment;
import plmatmaplus.matmapluspl.entity.UserEntity;
import plmatmaplus.matmapluspl.repository.CommentRepository;
import plmatmaplus.matmapluspl.repository.UserRepository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CourseOpinionsServiceTest {

    @InjectMocks
    private CourseOpinionsService courseOpinionsService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentRepository commentRepository;

    private void prepareCourseOpinions(int fiveStars,int fourStars,int threeStars,int twoStars, int oneStars) {
        long courseId = 1;
        given(commentRepository.findAllByCourseIdCoursesAndStarsIs(courseId,5)).willReturn(getOpinions(fiveStars));
        given(commentRepository.findAllByCourseIdCoursesAndStarsIs(courseId,4)).willReturn(getOpinions(fourStars));
        given(commentRepository.findAllByCourseIdCoursesAndStarsIs(courseId,3)).willReturn(getOpinions(threeStars));
        given(commentRepository.findAllByCourseIdCoursesAndStarsIs(courseId,2)).willReturn(getOpinions(twoStars));
        given(commentRepository.findAllByCourseIdCoursesAndStarsIs(courseId,1)).willReturn(getOpinions(oneStars));
    }


    private List<Comment> prepareCommentsByLocalDatesWithTeenZeroZeroHourTime(LocalDate ... localDates) {
        List<Comment> comments = new ArrayList<>();
        for (LocalDate localDate : localDates) {
            Comment comment = new Comment("comment","username",localDate,"10:00",5);
            comments.add(comment);
        }
        return comments;
    }

    private List<Comment> prepareCommentsByTimesWithConstantLocalDate_2022_10_10(String ... time) {
        List<Comment> comments = new ArrayList<>();
        for (String timeToString : time) {
            Comment comment = new Comment("comment","username",LocalDate.of(2022,10,10),timeToString,5);
            comments.add(comment);
        }
        return comments;
    }

    private Comment getComment(LocalDate localDate, String time) {
        return new Comment("comment","username",localDate,time,5);
    }


    private List<Comment> getOpinions(int comments) {
        List<Comment> commentsList = new ArrayList<>();
        for (int i = 0; i < comments; i++) {
            commentsList.add(new Comment());
        }
        return commentsList;
    }

    @Test
    void getCourseOpinionSectionDTO_whenCourseNotHaveAnyOpinions_shouldReturnSectionWith0AllOpinions() {
        //given
        long courseId = 1;
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        prepareCourseOpinions(0,0,0,0,0);

        //when;
        CourseOpinionSectionDTO courseOpinionSectionDTO  = courseOpinionsService.getCourseOpinionSectionDTO(courseIDEnum);
        int expectedValueOfAllOpinions = 0;

        //then
        Assertions.assertEquals(expectedValueOfAllOpinions,courseOpinionSectionDTO.getAllOpinions());
    }

    @Test
    void getCourseOpinionSectionDTO_whenCourseHaveOneOpinion_shouldReturnSectionWithOneAllOpinions() {
        //given
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        prepareCourseOpinions(1,0,0,0,0);

        //when
        CourseOpinionSectionDTO courseOpinionSectionDTO  = courseOpinionsService.getCourseOpinionSectionDTO(courseIDEnum);
        int expectedValueOfAllOpinions = 1;

        //then
        Assertions.assertEquals(expectedValueOfAllOpinions,courseOpinionSectionDTO.getAllOpinions());
    }

    @Test
    void getCourseOpinionSectionDTO_whenCourseHaveFiftyOpinions_shouldReturnSectionWithFiftyAllOpinions() {
        //given
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        prepareCourseOpinions(10,10,10,10,10);

        //when
        CourseOpinionSectionDTO courseOpinionSectionDTO  = courseOpinionsService.getCourseOpinionSectionDTO(courseIDEnum);
        int expectedValueOfAllOpinions = 50;

        //then
        Assertions.assertEquals(expectedValueOfAllOpinions,courseOpinionSectionDTO.getAllOpinions());
    }

    @Test
    void getCourseOpinionSectionDTO_whenCourseHavePreparedOfStars_shouldReturnSectionWithPreparedStars() {
        //given
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        prepareCourseOpinions(10,9,8,7,6);

        //when
        CourseOpinionSectionDTO courseOpinionSectionDTO  = courseOpinionsService.getCourseOpinionSectionDTO(courseIDEnum);
        int expectedValueOfFiveStars = 10;
        int expectedValueOfFourStars = 9;
        int expectedValueOfThreeStars = 8;
        int expectedValueOfTwoStars = 7;
        int expectedValueOfOneStars = 6;

        //then
        Assertions.assertEquals(expectedValueOfFiveStars,courseOpinionSectionDTO.getFiveStarsOpinions());
        Assertions.assertEquals(expectedValueOfFourStars,courseOpinionSectionDTO.getFourStarsOpinions());
        Assertions.assertEquals(expectedValueOfThreeStars,courseOpinionSectionDTO.getThreeStarsOpinions());
        Assertions.assertEquals(expectedValueOfTwoStars,courseOpinionSectionDTO.getTwoStarsOpinions());
        Assertions.assertEquals(expectedValueOfOneStars,courseOpinionSectionDTO.getOneStarsOpinions());
    }

    @Test
    void getCourseOpinionSectionDTO_whenCourseAverageIsFive_shouldReturnSectionWithFiveAverage() {
        //given
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        prepareCourseOpinions(10,0,0,0,0);

        //when
        CourseOpinionSectionDTO courseOpinionSectionDTO  = courseOpinionsService.getCourseOpinionSectionDTO(courseIDEnum);
        String average = "5,0";

        //then
        Assertions.assertEquals(average,courseOpinionSectionDTO.getAverageStarsOpinions());
    }

    @Test
    void getCourseOpinionSectionDTO_whenCourseAverageIsThree_shouldReturnSectionWithThreeAverage() {
        //given
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        prepareCourseOpinions(10,0,0,0,10);

        //when
        CourseOpinionSectionDTO courseOpinionSectionDTO  = courseOpinionsService.getCourseOpinionSectionDTO(courseIDEnum);
        String average = "3,0";

        //then
        Assertions.assertEquals(average,courseOpinionSectionDTO.getAverageStarsOpinions());
    }

    @Test
    void getCourseOpinionSectionDTO_whenCourseAverageIsThreePointThree_shouldReturnSectionWithThreePointThreeAverage() {
        //given
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        prepareCourseOpinions(2,2,0,0,2);

        //when
        CourseOpinionSectionDTO courseOpinionSectionDTO  = courseOpinionsService.getCourseOpinionSectionDTO(courseIDEnum);
        String average = "3,3";

        //then
        Assertions.assertEquals(average,courseOpinionSectionDTO.getAverageStarsOpinions());
    }

    @Test
    void getCourseOpinionSectionDTO_whenCourseAverageIsThreePointThree_shouldReturn3FullStars() {
        //given
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        prepareCourseOpinions(2,2,0,0,2);

        //when
        CourseOpinionSectionDTO courseOpinionSectionDTO  = courseOpinionsService.getCourseOpinionSectionDTO(courseIDEnum);

        //then
        int expectedFullStars = 3;
        Assertions.assertEquals(expectedFullStars,courseOpinionSectionDTO.getFullStars());
    }

    @Test
    void getCourseOpinionSectionDTO_whenCourseAverageIsThreePointThree_shouldReturn1EmptyStars() {
        //given
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        prepareCourseOpinions(2,2,0,0,2);

        //when
        CourseOpinionSectionDTO courseOpinionSectionDTO  = courseOpinionsService.getCourseOpinionSectionDTO(courseIDEnum);

        //then
        int expectedEmptyStars = 1;
        Assertions.assertEquals(expectedEmptyStars,courseOpinionSectionDTO.getEmptyStars());
    }

    @Test
    void getCourseOpinionSectionDTO_whenCourseAverageIsThreePointThree_shouldReturn33FractionStarWidth() {
        //given
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        prepareCourseOpinions(2,2,0,0,2);

        //when
        CourseOpinionSectionDTO courseOpinionSectionDTO  = courseOpinionsService.getCourseOpinionSectionDTO(courseIDEnum);

        //then
        int expectedFractionStartWidth = 33;
        Assertions.assertEquals(expectedFractionStartWidth,courseOpinionSectionDTO.getFractionStarWidth());
    }

    @Test
    void getCourseOpinionSectionDTO_whenCourseAverageIsFive_shouldReturn5FullStars() {
        //given
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        prepareCourseOpinions(10,0,0,0,0);

        //when
        CourseOpinionSectionDTO courseOpinionSectionDTO  = courseOpinionsService.getCourseOpinionSectionDTO(courseIDEnum);

        //then
        int expectedFullStars = 5;
        Assertions.assertEquals(expectedFullStars,courseOpinionSectionDTO.getFullStars());
    }

    @Test
    void getCourseOpinionSectionDTO_whenCourseAverageIsFive_shouldReturn0EmptyStars() {
        //given
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        prepareCourseOpinions(10,0,0,0,0);

        //when
        CourseOpinionSectionDTO courseOpinionSectionDTO  = courseOpinionsService.getCourseOpinionSectionDTO(courseIDEnum);

        //then
        int expectedEmptyStars = 0;
        Assertions.assertEquals(expectedEmptyStars,courseOpinionSectionDTO.getEmptyStars());
    }

    @Test
    void getCourseOpinionSectionDTO_whenCourseAverageIsFive_shouldReturn0FractionStarWidth() {
        //given
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        prepareCourseOpinions(10,0,0,0,0);

        //when
        CourseOpinionSectionDTO courseOpinionSectionDTO  = courseOpinionsService.getCourseOpinionSectionDTO(courseIDEnum);

        //then
        int expectedFractionStartWidth = 0;
        Assertions.assertEquals(expectedFractionStartWidth,courseOpinionSectionDTO.getFractionStarWidth());
    }

    @Test
    void getCourseOpinionSectionDTO_whenCourseNotHaveAnyOpinions_shouldReturn0FullStars() {
        //given
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        prepareCourseOpinions(0,0,0,0,0);

        //when
        CourseOpinionSectionDTO courseOpinionSectionDTO  = courseOpinionsService.getCourseOpinionSectionDTO(courseIDEnum);

        //then
        int expectedFullStars = 0;
        Assertions.assertEquals(expectedFullStars,courseOpinionSectionDTO.getFullStars());
    }

    @Test
    void getCourseOpinionSectionDTO_whenCourseNotHaveAnyOpinions_shouldReturn0EmptyStars() {
        //given
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        prepareCourseOpinions(0,0,0,0,0);

        //when
        CourseOpinionSectionDTO courseOpinionSectionDTO  = courseOpinionsService.getCourseOpinionSectionDTO(courseIDEnum);

        //then
        int expectedEmptyStars = 0;
        Assertions.assertEquals(expectedEmptyStars,courseOpinionSectionDTO.getEmptyStars());
    }

    @Test
    void getCourseOpinionSectionDTO_whenCourseNotHaveAnyOpinions_shouldReturn0FractionStarWidth() {
        //given
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        prepareCourseOpinions(0,0,0,0,0);

        //when
        CourseOpinionSectionDTO courseOpinionSectionDTO  = courseOpinionsService.getCourseOpinionSectionDTO(courseIDEnum);

        //then
        int expectedFractionStartWidth = 0;
        Assertions.assertEquals(expectedFractionStartWidth,courseOpinionSectionDTO.getFractionStarWidth());
    }

    @Test
    void  getCourseComments_whenCourseNotHaveAnyComments_shouldReturnEmptyList() {
        //given
        long courseId = 1;
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        List<Comment> comments = new ArrayList<>();
        given(commentRepository.findAllByCourseIdCourses(courseId)).willReturn(comments);

        //when
        List<CourseCommentDTO> expectedListOfComments = List.of();

        //then
        List<CourseCommentDTO> actualListOfComments = courseOpinionsService.getCourseComments(courseIDEnum);
        Assertions.assertEquals(expectedListOfComments,actualListOfComments);
    }

    @Test
    void  getCourseComments_whenCourseHasTwoComments_shouldReturnListWithTwoComments() {
        //given
        long courseId = 1;
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        List<Comment> comments = prepareCommentsByLocalDatesWithTeenZeroZeroHourTime(LocalDate.of(2022,10,1),
                LocalDate.of(2022,10,1));
        given(commentRepository.findAllByCourseIdCourses(courseId)).willReturn(comments);

        //when
        int expectedListOfCommentsSize = 2;

        //then
       int actualListOfCommentsSize = courseOpinionsService.getCourseComments(courseIDEnum).size();
        Assertions.assertEquals(expectedListOfCommentsSize,actualListOfCommentsSize);
    }

    @Test
    void  getCourseComments_whenCourseHasFourComments_shouldReturnListWithCommentsSortedByDate() {
        //given
        long courseId = 1;
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        List<Comment> comments = prepareCommentsByLocalDatesWithTeenZeroZeroHourTime(
                LocalDate.of(2022,10,1),
                LocalDate.of(2022,10,2),
                LocalDate.of(2022,10,3),
                LocalDate.of(2022,10,4));
        given(commentRepository.findAllByCourseIdCourses(courseId)).willReturn(comments);

        //when
        String expectedLocalDateOfYoungestComment = "2022-10-04";

        //then
        String actualLocalDateOfYoungestComment = courseOpinionsService.getCourseComments(courseIDEnum).get(0).getDate();
        Assertions.assertEquals(expectedLocalDateOfYoungestComment,actualLocalDateOfYoungestComment);
    }

    @Test
    void  getCourseComments_whenCourseHasFourCommentsAndCommentsAreAddedRandomly_shouldReturnListWithCommentsSortedByDate() {
        //given
        long courseId = 1;
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        List<Comment> comments = prepareCommentsByLocalDatesWithTeenZeroZeroHourTime(
                LocalDate.of(2022,10,1),
                LocalDate.of(2022,10,2),
                LocalDate.of(2022,10,4),
                LocalDate.of(2022,10,3));
        given(commentRepository.findAllByCourseIdCourses(courseId)).willReturn(comments);

        //when
        String expectedLocalDateOfYoungestComment = "2022-10-04";

        //then
        String actualLocalDateOfYoungestComment = courseOpinionsService.getCourseComments(courseIDEnum).get(0).getDate();
        Assertions.assertEquals(expectedLocalDateOfYoungestComment,actualLocalDateOfYoungestComment);
    }

    @Test
    void  getCourseComments_whenCourseHasFourComments_shouldReturnListWithCommentsSortedByTime() {
        //given
        long courseId = 1;
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        List<Comment> comments = prepareCommentsByTimesWithConstantLocalDate_2022_10_10(
                "10:50","11:49","13:54","14:21");
        given(commentRepository.findAllByCourseIdCourses(courseId)).willReturn(comments);


        //when
        String expectedTimeOfYoungestComment = "14:21";

        //then
        String actualTimeOfYoungestComment = courseOpinionsService.getCourseComments(courseIDEnum).get(0).getTime();
        Assertions.assertEquals(expectedTimeOfYoungestComment,actualTimeOfYoungestComment);
    }

    @Test
    void  getCourseComments_whenCourseHasFourCommentsAddedRandomly_shouldReturnListWithCommentsSortedByTime() {
        //given
        long courseId = 1;
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        List<Comment> comments = prepareCommentsByTimesWithConstantLocalDate_2022_10_10(
                "15:50","20:49","13:54","14:21");
        given(commentRepository.findAllByCourseIdCourses(courseId)).willReturn(comments);


        //when
        String expectedTimeOfYoungestComment = "20:49";

        //then
        String actualTimeOfYoungestComment = courseOpinionsService.getCourseComments(courseIDEnum).get(0).getTime();
        Assertions.assertEquals(expectedTimeOfYoungestComment,actualTimeOfYoungestComment);
    }

    @Test
    void  getCourseComments_whenCourseHasFourComments_shouldReturnListWithCommentsSortedByYoungestComment() {
        //given
        long courseId = 1;
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        List<Comment> comments = List.of(
                getComment(LocalDate.of(2022,9,10),"15:00"),
                getComment(LocalDate.of(2022,10,5),"20:00"),
                getComment(LocalDate.of(2022,11,7),"21:00"),
                getComment(LocalDate.of(2022,12,1),"10:00")
        );
        given(commentRepository.findAllByCourseIdCourses(courseId)).willReturn(comments);


        //when
        String expectedDateOfYoungestComment = "2022-12-01";
        String expectedTimeOfYoungestComment = "10:00";

        //then
        String actualDateOfYoungestComment = courseOpinionsService.getCourseComments(courseIDEnum).get(0).getDate();
        String actualTimeOfYoungestComment = courseOpinionsService.getCourseComments(courseIDEnum).get(0).getTime();
        Assertions.assertEquals(expectedTimeOfYoungestComment,actualTimeOfYoungestComment);
        Assertions.assertEquals(expectedDateOfYoungestComment,actualDateOfYoungestComment);
    }

    @Test
    void  getCourseComments_whenCourseHasFourCommentsAddedRandomly_shouldReturnListWithCommentsSortedByYoungestComment() {
        //given
        long courseId = 1;
        CourseID courseIDEnum = CourseID.BASE_MATH_ANALYSIS;
        List<Comment> comments = List.of(
                getComment(LocalDate.of(2022,9,10),"15:00"),
                getComment(LocalDate.of(2022,12,1),"10:00"),
                getComment(LocalDate.of(2022,11,7),"21:00"),
                getComment(LocalDate.of(2022,10,5),"20:00")

        );
        given(commentRepository.findAllByCourseIdCourses(courseId)).willReturn(comments);


        //when
        String expectedDateOfYoungestComment = "2022-12-01";
        String expectedTimeOfYoungestComment = "10:00";

        //then
        String actualDateOfYoungestComment = courseOpinionsService.getCourseComments(courseIDEnum).get(0).getDate();
        String actualTimeOfYoungestComment = courseOpinionsService.getCourseComments(courseIDEnum).get(0).getTime();
        Assertions.assertEquals(expectedTimeOfYoungestComment,actualTimeOfYoungestComment);
        Assertions.assertEquals(expectedDateOfYoungestComment,actualDateOfYoungestComment);
    }

    @Test
    void isUserExist_whenSessionIsActive_shouldReturnTrue() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";
        int idUser = 1;
        session.setAttribute(attributeUser,idUser);
        given(request.getSession()).willReturn(session);
        given(request.getSession().getAttribute(attributeUser)).willReturn(idUser);


        //when
        boolean isNoActiveSession = courseOpinionsService.isUserExist(request);

        //then
        Assertions.assertTrue(isNoActiveSession);
    }

    @Test
    void isUserExist_whenSessionIsNoActive_shouldReturnFalse() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";
        given(request.getSession()).willReturn(session);
        given(request.getSession().getAttribute(attributeUser)).willReturn(null);


        //when
        boolean isNoActiveSession =  courseOpinionsService.isUserExist(request);

        //then
        Assertions.assertFalse(isNoActiveSession);
    }

    @Test
    void isOpinionPicked_whenPickedStarsIsBiggerThanZero_shouldReturnTrue() {
        //give
        int pickedStars = 2;

        //then
        boolean actualResult = courseOpinionsService.isOpinionPicked(pickedStars);
        Assertions.assertTrue(actualResult);
    }

    @Test
    void isOpinionPicked_whenPickedStarsIsEqualsToZero_shouldReturnFalse() {
        //give
        int pickedStars = 0;

        //then
        boolean actualResult = courseOpinionsService.isOpinionPicked(pickedStars);
        Assertions.assertFalse(actualResult);
    }

    @Test
    void getRedirectViewByCourseId_whenCourseIdIs1_shouldReturnBaseMathAnalysisRedirectedViewToString() {
        //given
        int courseId = 1;

        //then
        String expectedRedirectView = "redirect:/matmaplus/shop/analizapodstawa";
        String actualRedirectView = courseOpinionsService.getRedirectViewByCourseId(courseId);
        Assertions.assertEquals(expectedRedirectView,actualRedirectView);
    }

    @Test
    void getRedirectViewByCourseId_whenCourseIdIs2_shouldReturnExtendedMathAnalysisRedirectedViewToString() {
        //given
        int courseId = 2;

        //then
        String expectedRedirectView = "redirect:/matmaplus/shop/analizarozszerzona";
        String actualRedirectView = courseOpinionsService.getRedirectViewByCourseId(courseId);
        Assertions.assertEquals(expectedRedirectView,actualRedirectView);
    }

    @Test
    void getRedirectViewByCourseId_whenCourseIdIs3_shouldReturnBaseExamRedirectedViewToString() {
        //given
        int courseId = 3;

        //then
        String expectedRedirectView = "redirect:/matmaplus/shop/maturapodstawowa";
        String actualRedirectView = courseOpinionsService.getRedirectViewByCourseId(courseId);
        Assertions.assertEquals(expectedRedirectView,actualRedirectView);
    }

    @Test
    void getRedirectViewByCourseId_whenCourseIdIs4_shouldReturnExtendedExamRedirectedViewToString() {
        //given
        int courseId = 4;

        //then
        String expectedRedirectView = "redirect:/matmaplus/shop/maturarozszerzona";
        String actualRedirectView = courseOpinionsService.getRedirectViewByCourseId(courseId);
        Assertions.assertEquals(expectedRedirectView,actualRedirectView);
    }

    @Test
    void getRedirectViewByCourseId_whenCourseIdIs5_shouldReturnPrimarySchoolExamRedirectedViewToString() {
        //given
        int courseId = 5;

        //then
        String expectedRedirectView = "redirect:/matmaplus/shop/egzamin-ósmioklasisty";
        String actualRedirectView = courseOpinionsService.getRedirectViewByCourseId(courseId);
        Assertions.assertEquals(expectedRedirectView,actualRedirectView);
    }

    @Test
    void getRedirectViewByCourseId_whenCourseIdIs6_shouldReturnIntegralsExamRedirectedViewToString() {
        //given
        int courseId = 6;

        //then
        String expectedRedirectView = "redirect:/matmaplus/shop/całki";
        String actualRedirectView = courseOpinionsService.getRedirectViewByCourseId(courseId);
        Assertions.assertEquals(expectedRedirectView,actualRedirectView);
    }

    @Test
    void isUserRatedCourse_whenUserWasRatedCourse_shouldReturnTrue() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";
        int idUser = 1;
        UserEntity userEntity = new UserEntity("username","password","email");
        session.setAttribute(attributeUser,idUser);
        given(request.getSession()).willReturn(session);
        given(request.getSession().getAttribute(attributeUser)).willReturn(idUser);
        given(userRepository.findByIdUsers((long) idUser)).willReturn(Optional.of(userEntity));
        List<Comment> comments = new ArrayList<>();
        given(commentRepository.findAllByCourseIdCoursesAndUsername(idUser,"username")).willReturn(comments);

        //when
        comments.add(new Comment());

        //then
        Assertions.assertTrue(courseOpinionsService.isUserRatedCourse(request,idUser));


    }

    @Test
    void isUserRatedCourse_whenUserWasNotRatedCourse_shouldReturnFalse() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String attributeUser = "user";
        int idUser = 1;
        UserEntity userEntity = new UserEntity("username","password","email");
        session.setAttribute(attributeUser,idUser);
        given(request.getSession()).willReturn(session);
        given(request.getSession().getAttribute(attributeUser)).willReturn(idUser);
        given(userRepository.findByIdUsers((long) idUser)).willReturn(Optional.of(userEntity));
        List<Comment> comments = new ArrayList<>();
        given(commentRepository.findAllByCourseIdCoursesAndUsername(idUser,"username")).willReturn(comments);

        //then
        Assertions.assertFalse(courseOpinionsService.isUserRatedCourse(request,idUser));
    }





}