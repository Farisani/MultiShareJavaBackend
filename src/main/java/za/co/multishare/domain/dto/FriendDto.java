package za.co.multishare.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendDto {

    private Long userId;
    private String surname;
    private String name;
    private String friendshipStatus;
    private Boolean canAccept;
}
