package za.co.multishare.web.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.multishare.business.service.FriendsService;
import za.co.multishare.business.service.UserDetailService;
import za.co.multishare.domain.dto.FriendDto;
import za.co.multishare.domain.dto.FriendRequestDto;
import za.co.multishare.domain.entity.FriendshipInfo;
import za.co.multishare.domain.entity.UserInfo;
import za.co.multishare.domain.entity.UserInfoDetail;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/friends/")
public class FriendsController {

    private final FriendsService friendsService;
    private final UserDetailService userDetailService;

    @Autowired
    public FriendsController(final FriendsService friendsService,
                             final UserDetailService userDetailService) {
        this.friendsService = friendsService;
        this.userDetailService = userDetailService;
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

    @GetMapping("/get/friendships/{userId}")
    public ResponseEntity<List<FriendDto>> getFriends(@PathVariable final Long userId,
                                                      @RequestParam final Integer pageNumber,
                                                      @RequestParam final Integer pageSize) {
        final List<FriendshipInfo> friendshipInfoList = friendsService.findFriendships(userId, pageNumber, pageSize);

        List<FriendDto> friendDtoList = new ArrayList<>();

        friendshipInfoList.forEach(friendshipInfo -> {
           final UserInfo friendshipFriendUserInfo;

            if (!friendshipInfo.getSrcFriendshipUserInfo().getUserInfoId().equals(userId)) {
                friendshipFriendUserInfo = friendshipInfo.getSrcFriendshipUserInfo();
            } else {
                friendshipFriendUserInfo = friendshipInfo.getDestFriendshipUserInfo();
            }

            final UserInfoDetail userInfoDetail = userDetailService.findActive(friendshipFriendUserInfo.getUserInfoId());

            friendDtoList.add(new FriendDto(userInfoDetail.getUserInfo().getUserInfoId(),
                    userInfoDetail.getSurname(),
                    userInfoDetail.getName(),
                    friendshipInfo.getFriendshipInfoStatus()));
        });

        return new ResponseEntity<>(friendDtoList, HttpStatus.OK);
    }

}
