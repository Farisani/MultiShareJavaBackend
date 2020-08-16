package za.co.multishare.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class RetrievePostDto {

    @Getter
    @Setter
    private List<PostDto> postDtoList;

    @Getter
    @Setter
    private Boolean hasNext;
}
