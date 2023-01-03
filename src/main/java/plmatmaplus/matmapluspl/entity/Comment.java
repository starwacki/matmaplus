package plmatmaplus.matmapluspl.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcomments")
    private Long idComments;

    @NonNull
    private String comment;

    @NonNull
    private String username;

    @NonNull
    private LocalDate date;

    @NonNull
    private String time;

    @NonNull
    private Integer stars;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courses_idcourses")
    Course course;

    @Override
    public String toString() {
        return "Comment{" +
                "idComments=" + idComments +
                ", comment='" + comment + '\'' +
                ", username='" + username + '\'' +
                ", date=" + date +
                ", time='" + time + '\'' +
                ", stars=" + stars +
                '}';
    }
}
