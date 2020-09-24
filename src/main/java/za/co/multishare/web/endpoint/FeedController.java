package za.co.multishare.web.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.multishare.web.service.FeedService;
import za.co.multishare.web.domain.dto.FeedDto;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feedService;

    @Autowired
    public FeedController(final FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<List<FeedDto>> getUserFeed(@PathVariable final Long userId,
                                                     @RequestParam final Integer pageNumber,
                                                     @RequestParam final Integer pageSize) {
        final List<FeedDto> feedDtoList = feedService.getFeeds(userId, pageNumber, pageSize);

        return new ResponseEntity<>(feedDtoList, HttpStatus.OK);
    }
}
