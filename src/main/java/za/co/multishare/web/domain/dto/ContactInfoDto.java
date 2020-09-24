package za.co.multishare.web.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class ContactInfoDto {

    @Getter
    @Setter
    private Long userInfoId;

    @Getter
    @Setter
    private String contact;

    @Getter
    @Setter
    private String contactType;

    @Getter
    @Setter
    private LocalDateTime recordValidFromDate;

    @Getter
    @Setter
    private  LocalDateTime recordValidToDate;
}
