package za.co.multishare.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrationDto {

    private String title;
    private String name;
    private String surname;
    private String gender;
    private String legalIdentityNumber;
    private String contactNumber;
    private String emailAddress;
    private String password;
    private List<String> roles;
}
