package za.co.multishare.web.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ContactDetailsInfoDto {
    @Getter
    @Setter
    private String contact;

    @Getter
    @Setter
    private String contactType;
}
