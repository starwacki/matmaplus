package plmatmaplus.matmapluspl.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CourseCartDTO {

    private Long idCourses;

    private String name;

    private Double price;

    private String advancement;

    private String imgLink;


}

