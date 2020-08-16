package za.co.multishare.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    @Getter
    @Setter
    private Long postId;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String postBody;

    @Getter
    @Setter
    private LocalDateTime dateCreated;

    @Getter
    @Setter
    private List<String> resources;
}
