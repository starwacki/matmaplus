package plmatmaplus.matmapluspl.entity;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Course {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "idcourses")
    private Long idCourses;

    private String name;

    private Double price;

    private String advancement;

    @ManyToMany(mappedBy = "courses",fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

}
