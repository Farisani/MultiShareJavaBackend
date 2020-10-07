package za.co.multishare.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginInternalDto {

    private String username;
    private String password;
    private List<String> roles;

}
