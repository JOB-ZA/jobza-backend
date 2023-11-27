package jobza.recommend.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChartResponse {
    private List<String> names;
    private List<Float> pie;
    private List<Integer> min;
    private List<Integer> max;
}
