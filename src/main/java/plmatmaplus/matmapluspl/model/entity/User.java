package plmatmaplus.matmapluspl.model.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusers")
    private Long idUsers;

    private String username;

    private String password;

    private String email;

    @ManyToMany (
            cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "users_courses",
            joinColumns = @JoinColumn(name = "users_idusers"),
            inverseJoinColumns = @JoinColumn(name =  "courses_idcourses")
    )
    private Set<Course> courses = new HashSet<>();


    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void addCourseToUser(Course course) {
        if (courses==null) {
            courses = new HashSet<>();
            courses.add(course);
        }
        else courses.add(course);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return idUsers;
    }

    public void setUserId(Long userId) {
        this.idUsers = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + idUsers +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
