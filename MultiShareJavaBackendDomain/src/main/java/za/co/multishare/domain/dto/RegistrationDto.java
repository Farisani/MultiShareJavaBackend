package za.co.multishare.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

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
    private String legalIdentityNumber;

    @Getter
    @Setter
    private String contactNumber;

    @Getter
    @Setter
    private String emailAddress;

    @Getter
    @Setter
    private String password;
}
