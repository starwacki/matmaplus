package plmatmaplus.matmapluspl.entity;


import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "promo_codes")
public class PromoCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String code;

    @NonNull
    @Column(name = "percent_value")
    private Integer percentValue;
}
