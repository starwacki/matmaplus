package plmatmaplus.matmapluspl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plmatmaplus.matmapluspl.dto.CourseCommentDTO;
import plmatmaplus.matmapluspl.dto.CourseOpinionSectionDTO;
import plmatmaplus.matmapluspl.entity.Comment;
import plmatmaplus.matmapluspl.repository.CommentRepository;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class CourseOpinionsService {

    private final CommentRepository commentRepository;

    @Autowired
    CourseOpinionsService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CourseOpinionSectionDTO getCourseOpinionSectionDTO (long courseId) {
        CourseOpinionSectionDTO courseOpinionSectionDTO = createCourseOpinionSectionDTO(courseId);
        setAverageAndAllOpinions(courseOpinionSectionDTO);
        setCourseStars(courseOpinionSectionDTO);
        return courseOpinionSectionDTO;
    }

    public List<CourseCommentDTO> getCourseComments(long courseId) {
        return commentRepository.findAllByCourseIdCourses(courseId)
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

    public boolean isUserExist(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null;
    }

    public boolean isOpinionPicked(int stars) {
        return stars != 0;
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
        return new CourseCommentDTO(
                comment.getComment(),
                comment.getUsername(),
                comment.getDate().toString(),
                comment.getTime(),
                comment.getStars(),
                getEmptyStars(comment.getStars())
        );
    }


}
