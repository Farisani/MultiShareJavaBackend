package za.co.multishare.web.service;

import za.co.multishare.web.domain.dto.RetrievePostDto;

import java.time.LocalDateTime;
import java.util.List;

public interface PostManagerService {

    Long createPost(Long userInfoId,
                    String postTitle,
                    String postBody,
                    List<String> postResources,
                    LocalDateTime recordValidFromDate,
                    LocalDateTime recordValidToDate);

    RetrievePostDto getPosts(Long userId,
                             Integer pageNumber,
                             Integer pageSize);

    Boolean deletePost(Long postId);
}
