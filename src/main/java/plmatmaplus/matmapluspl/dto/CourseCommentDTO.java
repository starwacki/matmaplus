package plmatmaplus.matmapluspl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class CourseCommentDTO {

    private String comment;

    private String username;

    private String date;

    private String time;

    private Integer fullStars;

    private Integer emptyStars;

}
