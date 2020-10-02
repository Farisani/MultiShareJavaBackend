package za.co.multishare.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class FeedDto {

    @Getter
    @Setter
    private Long userInfoId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String surname;

    @Getter
    @Setter
    private String postTitle;

    @Getter
    @Setter
    private String postBody;

    @Getter
    @Setter
    private List<String> postResourceList;

    @Getter
    @Setter
    private LocalDateTime datePostMade;

}
