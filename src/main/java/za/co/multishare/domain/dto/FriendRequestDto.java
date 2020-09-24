package za.co.multishare.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestDto {

    @Getter
    @Setter
    private Long friendShipSourceUserInfoId;

    @Getter
    @Setter
    private Long friendShipDestinationUserInfoId;
}
