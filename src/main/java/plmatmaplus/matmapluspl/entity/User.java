package plmatmaplus.matmapluspl.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusers")
    private Long idUsers;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String email;

    @ManyToMany (
            cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "users_courses",
            joinColumns = @JoinColumn(name = "users_idusers"),
            inverseJoinColumns = @JoinColumn(name =  "courses_idcourses")
    )
    private Set<Course> courses = new HashSet<>();

    public void addCourseToUser(Course course) {
        if (courses==null) {
            courses = new HashSet<>();
            courses.add(course);
        }
        else courses.add(course);
    }

}
