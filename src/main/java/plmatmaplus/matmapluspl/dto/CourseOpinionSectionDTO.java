package plmatmaplus.matmapluspl.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class CourseOpinionSectionDTO {

    @NonNull
    private int fiveStarsOpinions;

    @NonNull
    private int fourStarsOpinions;

    @NonNull
    private int threeStarsOpinions;

    @NonNull
    private int twoStarsOpinions;

    @NonNull
    private int oneStarsOpinions;

    private String averageStarsOpinions;

    private int allOpinions;

}
