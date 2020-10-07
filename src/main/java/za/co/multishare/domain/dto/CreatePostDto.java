package za.co.multishare.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDto {

    @Getter
    @Setter
    private Long userId;

    @Setter
    @Getter
    private String title;

    @Getter
    @Setter
    private String postBody;

}
