package plmatmaplus.matmapluspl.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import plmatmaplus.matmapluspl.controller.CourseID;
import plmatmaplus.matmapluspl.controller.RedirectViews;
import plmatmaplus.matmapluspl.dto.CourseCommentDTO;
import plmatmaplus.matmapluspl.dto.CourseOpinionSectionDTO;
import plmatmaplus.matmapluspl.entity.Comment;
import plmatmaplus.matmapluspl.entity.Course;
import plmatmaplus.matmapluspl.repository.CommentRepository;
import plmatmaplus.matmapluspl.repository.CourseRepository;
import plmatmaplus.matmapluspl.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@AllArgsConstructor
public class CourseOpinionsService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;


    public CourseOpinionSectionDTO getCourseOpinionSectionDTO (CourseID courseId) {
        CourseOpinionSectionDTO courseOpinionSectionDTO = createCourseOpinionSectionDTO(courseId.getCourseId());
        setAverageAndAllOpinions(courseOpinionSectionDTO);
        setCourseStars(courseOpinionSectionDTO);
        return courseOpinionSectionDTO;
    }

    public List<CourseCommentDTO> getCourseComments(CourseID courseId) {
        return commentRepository.findAllByCourseIdCourses(courseId.getCourseId())
                .stream()
                .sorted((o1, o2) -> {
                    if (o1.getDate().compareTo(o2.getDate())!=0)
                        return -o1.getDate().compareTo(o2.getDate());
                    else if (getHoursFromStringTime(o1) != getHoursFromStringTime(o2))
                        return -Integer.compare(getHoursFromStringTime(o1),getHoursFromStringTime(o2));
                    else
                        return -Integer.compare(getMinutesFromStringTime(o1),getMinutesFromStringTime(o2));
                })
                .map(comment -> mapToCourseCommentDTO(comment))
                .toList();
    }

    public void addCommentToCourse(long courseId,String comment,
                                   HttpServletRequest request, int stars) {
        commentRepository.save(createComment(comment,request,stars,courseId));
    }

    public boolean isUserExist(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null;
    }

    public boolean isOpinionPicked(int stars) {
        return stars != 0;
    }

    public String getRedirectViewByCourseId(int courseID) {
        String view = "";
        switch (courseID) {
            case 1 -> view = RedirectViews.BASE_MATH_ANALYSIS_VIEW.toString();
            case 2 ->  view = RedirectViews.EXTENDED_MATH_ANALYSIS_VIEW.toString();
            case 3 -> view = RedirectViews.BASE_EXAM_VIEW.toString();
            case 4 -> view = RedirectViews.EXTENDED_EXAM_VIEW.toString();
            case 5 -> view = RedirectViews.PRIMARY_SCHOOL_EXAM_VIEW.toString();
            case 6 ->  view = RedirectViews.INTEGRALS_VIEW.toString();
        }
        return view;
    }

    public boolean isUserRatedCourse(HttpServletRequest request,int courseId) {
       return !commentRepository.findAllByCourseIdCoursesAndUsername( courseId,
                getUsername(request)).isEmpty();
    }



    private Comment createComment(String comment, HttpServletRequest request, int stars,long courseId) {
        Comment com= new Comment(comment, getUsername(request), LocalDate.now(), getTime(), stars);
        com.setCourse(getCourse(courseId));
        return com;
    }

    private Course getCourse(long courseId) {
        return courseRepository.findByIdCourses(courseId).get();
    }

    private String getUsername(HttpServletRequest request) {
        return userRepository.findByIdUsers(getUserId(request)).get().getUsername();
    }

    private long getUserId(HttpServletRequest request) {
        return Long.parseLong(request.getSession().getAttribute("user").toString());
    }

    private String getTime() {
        return LocalTime.now().getHour() + ":" + LocalTime.now().getMinute();
    }


    private int getFullCourseStars(double average) {
        return (int) (average);
    }

    private int getFractionOfStarsInPercent(double average) {
          return (int) ((average-getFullCourseStars(average))*100);
    }

    private int getEmptyStars(double average) {
        return (int) (5-average);
    }

    private CourseOpinionSectionDTO createCourseOpinionSectionDTO(long courseId) {
        return new CourseOpinionSectionDTO(
                getStarsOpinions(courseId,5),
                getStarsOpinions(courseId,4),
                getStarsOpinions(courseId,3),
                getStarsOpinions(courseId,2),
                getStarsOpinions(courseId,1)
        );
    }

    private void setAverageAndAllOpinions(CourseOpinionSectionDTO courseOpinionSectionDTO) {
        courseOpinionSectionDTO.setAllOpinions(getAllOpinions(courseOpinionSectionDTO));
        courseOpinionSectionDTO.setAverageStarsOpinions(new DecimalFormat("#.0")
                .format(getAverageStarsOpinions(courseOpinionSectionDTO)));
    }

    private int getAllOpinions(CourseOpinionSectionDTO courseOpinionSectionDTO) {
        return  courseOpinionSectionDTO.getOneStarsOpinions() +
                courseOpinionSectionDTO.getTwoStarsOpinions() +
                courseOpinionSectionDTO.getThreeStarsOpinions() +
                courseOpinionSectionDTO.getFourStarsOpinions() +
                courseOpinionSectionDTO.getFiveStarsOpinions();
    }

    private void setCourseStars(CourseOpinionSectionDTO courseOpinionSectionDTO) {
        double average = getAverage(courseOpinionSectionDTO);
        courseOpinionSectionDTO.setFullStars(getFullCourseStars(average));
        courseOpinionSectionDTO.setEmptyStars(getEmptyStars(average));
        courseOpinionSectionDTO.setFractionStarWidth(getFractionOfStarsInPercent(average));
    }

    private double getAverageStarsOpinions(CourseOpinionSectionDTO courseOpinionSectionDTO) {
        if (isCourseHaveOpinions(courseOpinionSectionDTO))
            return getAverage(courseOpinionSectionDTO);
        else return 0;
    }

    private boolean isCourseHaveOpinions(CourseOpinionSectionDTO courseOpinionSectionDTO) {
        return getAllOpinions(courseOpinionSectionDTO)!=0;
    }

    private double getAverage(CourseOpinionSectionDTO courseOpinionSectionDTO) {
      return   (courseOpinionSectionDTO.getFiveStarsOpinions()*5 +
               courseOpinionSectionDTO.getFourStarsOpinions()*4 +
              courseOpinionSectionDTO.getThreeStarsOpinions()*3 +
              courseOpinionSectionDTO.getTwoStarsOpinions()*2 +
              courseOpinionSectionDTO.getOneStarsOpinions())
              /(double) getAllOpinions(courseOpinionSectionDTO);
    }

    private int getStarsOpinions(long courseId,int starsOpinions) {
        return commentRepository.findAllByCourseIdCoursesAndStarsIs(courseId,starsOpinions).size();
    }

    private int getEmptyStars(int stars) {
        return 5-stars;
    }

    private int getHoursFromStringTime(Comment comment) {
        return Integer.parseInt(comment.getTime().split(":")[0]);
    }

    private int getMinutesFromStringTime(Comment comment) {
        return Integer.parseInt(comment.getTime().split(":")[1]);
    }

    private CourseCommentDTO mapToCourseCommentDTO(Comment comment) {
        return CourseCommentDTO.builder()
                .comment(comment.getComment())
                .username(comment.getUsername())
                .date(comment.getDate().toString())
                .time(comment.getTime())
                .fullStars(comment.getStars())
                .emptyStars(getEmptyStars(comment.getStars()))
                .build();
    }


}
