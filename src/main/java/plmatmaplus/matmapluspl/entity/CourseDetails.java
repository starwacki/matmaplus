package plmatmaplus.matmapluspl.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "courses_details")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class CourseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String imgLink;

    @NonNull
    private Integer lessons;


    @NonNull
    private Double courseVideosTime;

    @OneToOne
            (fetch = FetchType.EAGER)
    @JoinColumn
            (name = "idcourses")
    private Course course;
}
