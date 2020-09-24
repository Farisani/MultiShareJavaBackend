package za.co.multishare.web.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.multishare.web.service.FriendsService;
import za.co.multishare.web.domain.dto.FriendRequestDto;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/friends/")
public class FriendsController {

    private final FriendsService friendsService;

    @Autowired
    public FriendsController(final FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @PostMapping("/request/create")
    public ResponseEntity<Long> sendFriendRequest(@RequestBody final FriendRequestDto friendRequestDto) {

        final Long newFriendshipId = friendsService.sendFriendRequest(friendRequestDto.getFriendShipSourceUserInfoId(),
                friendRequestDto.getFriendShipDestinationUserInfoId(), LocalDateTime.now(), null);
        return new ResponseEntity<>(newFriendshipId, HttpStatus.CREATED);
    }

    @GetMapping("/request/accept")
    public ResponseEntity<Boolean> acceptFriendRequest(@RequestParam final Long friendShipDestinationUserInfoId,
                                              @RequestParam final Long friendshipInfoId) {
        friendsService.acceptFriendRequest(friendShipDestinationUserInfoId, friendshipInfoId, LocalDateTime.now(), null);

        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

}
