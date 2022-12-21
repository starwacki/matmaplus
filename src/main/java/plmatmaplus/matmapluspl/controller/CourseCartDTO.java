package plmatmaplus.matmapluspl.controller;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseCartDTO {

    private Long idCourses;

    private String name;

    private Double price;

    private String advancement;

    private String imgLink;
}
