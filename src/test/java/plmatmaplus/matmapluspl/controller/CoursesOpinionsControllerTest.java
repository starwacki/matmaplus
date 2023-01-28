package plmatmaplus.matmapluspl.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plmatmaplus.matmapluspl.service.CartService;
import plmatmaplus.matmapluspl.service.CourseOpinionsService;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CoursesOpinionsControllerTest {

    @InjectMocks
    private CoursesOpinionsController coursesOpinionsController;
    @Mock
    private CourseOpinionsService courseOpinionsService;
    @Mock
    private CartService cartService;

    @Test
    void comment_whenUserNoExist_shouldReturnUserMustLoginView() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        given(courseOpinionsService.isUserExist(request)).willReturn(false);

        //then
        String expectedReturnedView = RedirectViews.USER_MUST_LOGIN_VIEW.toString();
        String actualReturnedView  = coursesOpinionsController.comment(1,1,"comment",request);
        Assertions.assertEquals(expectedReturnedView,actualReturnedView);
    }

    @Test
    void comment_whenUserRatedCourse_shouldReturnUserRatedCourseView() {
        //given
        int rating = 1;
        int courseId = 1;
        String comment = "comment";
        HttpServletRequest request = mock(HttpServletRequest.class);
        given(courseOpinionsService.isUserExist(request)).willReturn(true);
        given(courseOpinionsService.isUserRatedCourse(request,courseId)).willReturn(true);
        given(courseOpinionsService.getRedirectViewByCourseId(courseId)).willReturn(Views.BASE_MATH_ANALYSIS_VIEW.toString());


        //then
        String expectedReturnedView = Views.BASE_MATH_ANALYSIS_VIEW + "?rated";
        String actualReturnedView  = coursesOpinionsController.comment(rating,courseId,comment,request);
        Assertions.assertEquals(expectedReturnedView,actualReturnedView);
    }

    @Test
    void comment_whenUserNoRatedCourse_shouldReturnOpinionPickedCourseView() {
        //given
        int rating = 1;
        int courseId = 1;
        String comment = "comment";
        HttpServletRequest request = mock(HttpServletRequest.class);
        given(courseOpinionsService.isUserExist(request)).willReturn(true);
        given(courseOpinionsService.isUserRatedCourse(request,courseId)).willReturn(false);
        given(courseOpinionsService.isOpinionPicked(rating)).willReturn(false);
        given(courseOpinionsService.getRedirectViewByCourseId(courseId)).willReturn(Views.BASE_MATH_ANALYSIS_VIEW.toString());


        //then
        String expectedReturnedView = Views.BASE_MATH_ANALYSIS_VIEW + "?opinionPicked";
        String actualReturnedView  = coursesOpinionsController.comment(rating,courseId,comment,request);
        Assertions.assertEquals(expectedReturnedView,actualReturnedView);
    }

    @Test
    void comment_whenUserExistAndNoRatedCourseAndOpinionPicked_shouldReturnCourseView() {
        //given
        int rating = 1;
        int courseId = 1;
        String comment = "comment";
        HttpServletRequest request = mock(HttpServletRequest.class);
        given(courseOpinionsService.isUserExist(request)).willReturn(true);
        given(courseOpinionsService.isUserRatedCourse(request,courseId)).willReturn(false);
        given(courseOpinionsService.isOpinionPicked(rating)).willReturn(true);
        given(courseOpinionsService.getRedirectViewByCourseId(courseId)).willReturn(Views.BASE_MATH_ANALYSIS_VIEW.toString());


        //then
        String expectedReturnedView = Views.BASE_MATH_ANALYSIS_VIEW.toString();
        String actualReturnedView  = coursesOpinionsController.comment(rating,courseId,comment,request);
        Assertions.assertEquals(expectedReturnedView,actualReturnedView);
    }

    @Test
    void comment_whenUserExistAndNoRatedCourseAndOpinionPicked_shouldAddComment() {
        //given
        int rating = 1;
        int courseId = 1;
        String comment = "comment";
        HttpServletRequest request = mock(HttpServletRequest.class);
        given(courseOpinionsService.isUserExist(request)).willReturn(true);
        given(courseOpinionsService.isUserRatedCourse(request,courseId)).willReturn(false);
        given(courseOpinionsService.isOpinionPicked(rating)).willReturn(true);
        given(courseOpinionsService.getRedirectViewByCourseId(courseId)).willReturn(Views.BASE_MATH_ANALYSIS_VIEW.toString());


        //then
        coursesOpinionsController.comment(rating,courseId,comment,request);
        verify(courseOpinionsService).addCommentToCourse(courseId,comment,request,rating);
    }


}