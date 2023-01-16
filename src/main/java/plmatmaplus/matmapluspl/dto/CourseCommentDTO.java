package plmatmaplus.matmapluspl.dto;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class CourseCommentDTO {

    private String comment;

    private String username;

    private String date;

    private String time;

    private Integer fullStars;

    private Integer emptyStars;

}
