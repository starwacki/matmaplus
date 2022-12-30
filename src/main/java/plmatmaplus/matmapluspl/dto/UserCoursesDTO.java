package plmatmaplus.matmapluspl.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCoursesDTO {

    private Long idCourses;

    private String name;

    private String advancement;

    private String imgLink;

    private int lessons;

    private double courseVideosTime;

}
