package za.co.multishare.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendDto {

    Long userId;
    String surname;
    String name;
    String friendshipStatus;
}
