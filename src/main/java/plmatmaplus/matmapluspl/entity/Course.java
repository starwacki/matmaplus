package plmatmaplus.matmapluspl.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "idcourses")
    private Long idCourses;

    private String name;

    private Double price;

    private String advancement;

    @ManyToMany
            (mappedBy = "courses",fetch = FetchType.EAGER)
    private Set<UserEntity> userEntities = new HashSet<>();

    @ManyToMany
            (mappedBy = "courses", fetch = FetchType.EAGER)
    private Set<Cart> carts = new HashSet<>();

    @OneToOne
            (mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private CourseDetails details;



    @Override
    public String toString() {
        return "Course{" +
                "idCourses=" + idCourses +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", advancement='" + advancement + '\'' +
                '}';
    }
}
