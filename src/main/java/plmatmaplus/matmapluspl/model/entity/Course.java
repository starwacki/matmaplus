package plmatmaplus.matmapluspl.model.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
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

    public Course() {
    }

    public Course(String name, Double price, String advancement) {
        this.name = name;
        this.price = price;
        this.advancement = advancement;
    }

    public Long getIdCourses() {
        return idCourses;
    }

    public void setIdCourses(Long idCourses) {
        this.idCourses = idCourses;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAdvancement() {
        return advancement;
    }

    public void setAdvancement(String advancement) {
        this.advancement = advancement;
    }


    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + idCourses +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", advancement='" + advancement + '\'' +
                '}';
    }
}
