package plmatmaplus.matmapluspl.entity;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class UserEntity {

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
            cascade = {CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_courses",
            joinColumns = @JoinColumn(name = "users_idusers"),
            inverseJoinColumns = @JoinColumn(name =  "courses_idcourses")
    )
    private Set<Course> courses = new HashSet<>();

    @ManyToMany (
            fetch = FetchType.EAGER
    )
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "iduser"),
            inverseJoinColumns = @JoinColumn(name = "idrole"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public String toString() {
        return "UserEntity{" +
                "idUsers=" + idUsers +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
