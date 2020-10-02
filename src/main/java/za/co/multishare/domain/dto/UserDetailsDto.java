package za.co.multishare.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {

    @Getter
    @Setter
    private Long Id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String surname;

    @Getter
    @Setter
    private String gender;

    @Getter
    @Setter
    private List<ContactDetailsInfoDto> contactDetailsInfoDtoList;

    @Getter
    @Setter
    private String legalIdentityNumber;
}
