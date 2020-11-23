package za.co.multishare.endpoint;

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
import za.co.multishare.domain.dto.PostDto;
import za.co.multishare.service.PostManagerService;
import za.co.multishare.domain.dto.CreatePostDto;
import za.co.multishare.domain.dto.RetrievePostDto;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostManagerService postManagerService;

    @Autowired
    public PostController(final PostManagerService postManagerService) {
        this.postManagerService = postManagerService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createPost(@RequestBody final CreatePostDto createPostDto) {

        final Long postInfoId = postManagerService.createPost(createPostDto.getUserId(),
                createPostDto.getTitle(), createPostDto.getPostBody(), new ArrayList<>(),
                LocalDateTime.now(), null);

        return new ResponseEntity<>(postInfoId, HttpStatus.CREATED);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<RetrievePostDto> getPosts(@PathVariable final Long userId,
                                                     @RequestParam final Integer pageNumber,
                                                     @RequestParam final Integer pageSize) {
        final RetrievePostDto retrievePostDto = postManagerService.getPosts(userId, pageNumber, pageSize);

        return new ResponseEntity<>(retrievePostDto, HttpStatus.OK);
    }


    @GetMapping("/get/post-body/{postId}")
    public ResponseEntity<String> getPost(@PathVariable final Long postId) {
        final String postBody = postManagerService.getPost(postId).getPostBody();

        return new ResponseEntity<>(postBody, HttpStatus.OK);
    }

    @GetMapping("/delete/{postId}")
    public ResponseEntity<Boolean> deletePost(@PathVariable final Long postId) {
        final Boolean result = postManagerService.deletePost(postId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
