package za.co.multishare.business.service;

import org.springframework.data.domain.Page;
import za.co.multishare.domain.entity.PostInfo;
import za.co.multishare.domain.entity.PostInfoDetail;
import za.co.multishare.domain.entity.PostInfoDetailResource;
import za.co.multishare.domain.entity.UserInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface PostInfoService {

    PostInfo createPostInfo(UserInfo userInfo,
                            String postTitle,
                            String PostBody,
                            List<String> resources,
                            LocalDateTime recordValidFromDate,
                            LocalDateTime recordValidToDate);

    PostInfoDetail createPostInfoDetail(PostInfo postInfo,
                                        String title,
                                        String postBody,
                                        LocalDateTime recordValidFromDate,
                                        LocalDateTime recordValidToDate);

    List<PostInfoDetailResource> createPostInfoDetailResource(PostInfoDetail postInfoDetail,
                                                        List<String> resource,
                                                        LocalDateTime recordValidFromDate,
                                                        LocalDateTime recordValidToDate);

    Page<PostInfo> findAllUserPostInfo(Long userId,
                                      Integer pageNumber,
                                      Integer pageSize);

    List<PostInfo> findAllUserPostInfo(Long userInfoId);

    PostInfoDetail findActivePostInfoDetailByPostInfoId(Long postInfoDetailId);

    List<PostInfoDetailResource> findPostInfoDetailResourcesByPostInfoDetailId(Long postInfoDetailId);

    void deletePost(Long postId);
}
