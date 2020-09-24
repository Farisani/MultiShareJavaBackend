package za.co.multishare.web.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    @Getter
    @Setter
    Long userInfoId;

    @Getter
    @Setter
    LocalDateTime recordValidFromDate;

    @Getter
    @Setter
    LocalDateTime recordValidToDate;
}
