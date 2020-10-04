package za.co.multishare.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminUserDetailsDto {

    private Long userId;
    private String surname;
    private String name;
    private LocalDateTime registrationDate;
}
