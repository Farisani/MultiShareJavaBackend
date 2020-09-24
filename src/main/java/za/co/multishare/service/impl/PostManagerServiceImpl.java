package za.co.multishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import za.co.multishare.service.PostInfoService;
import za.co.multishare.service.PostManagerService;
import za.co.multishare.service.UserInfoService;
import za.co.multishare.domain.dto.PostDto;
import za.co.multishare.domain.dto.RetrievePostDto;
import za.co.multishare.domain.entity.PostInfo;
import za.co.multishare.domain.entity.PostInfoDetail;
import za.co.multishare.domain.entity.PostInfoDetailResource;
import za.co.multishare.domain.entity.UserInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostManagerServiceImpl implements PostManagerService {

    private final UserInfoService userInfoService;
    private final PostInfoService postInfoService;

    @Autowired
    public PostManagerServiceImpl(final UserInfoService userInfoService,
                                  final PostInfoService postInfoService) {
        this.userInfoService = userInfoService;
        this.postInfoService = postInfoService;
    }

    @Override
    public Long createPost(final Long userInfoId,
                           final String postTitle,
                           final String postBody,
                           final List<String> postResources,
                           final LocalDateTime recordValidFromDate,
                           final LocalDateTime recordValidToDate) {

        final UserInfo userInfo = getUserInfo(userInfoId);

        final PostInfo postInfo = postInfoService.createPostInfo(userInfo, postTitle, postBody,
                postResources, recordValidFromDate, recordValidToDate);

        return postInfo.getPostInfoId();
    }

    @Override
    public RetrievePostDto getPosts(final Long userId,
                                          final Integer pageNumber,
                                          final Integer pageSize) {

        final List<PostDto> postDtoList = new ArrayList<>();

        final Page<PostInfo> postInfoPage = postInfoService.findAllUserPostInfo(userId, pageNumber,
                pageSize);

        postInfoPage.toList().forEach(postInfo -> {
            final PostInfoDetail postInfoDetail = retrievePostInfoDetail(postInfo.getPostInfoId());

            final List<PostInfoDetailResource> postInfoDetailResourceList =
                    retrievePostInfoDetailResourceList(postInfoDetail.getPostInfoDetailId());

            postDtoList.add(buildPostDto(postInfo, postInfoDetail, postInfoDetailResourceList));
        });
        return new RetrievePostDto(postDtoList, postInfoPage.hasNext());
    }

    @Override
    public Boolean deletePost(Long postId) {
        try {
            postInfoService.deletePost(postId);
            return Boolean.TRUE;
        } catch (Exception exception) {
            return Boolean.FALSE;
        }
    }

    private PostDto buildPostDto(final PostInfo postInfo,
                                 final PostInfoDetail postInfoDetail,
                                 final List<PostInfoDetailResource> postInfoDetailResourceList) {

        final PostDto postDto = new PostDto(postInfo.getPostInfoId(),
                postInfoDetail.getTitle(),
                postInfoDetail.getPostBody(),
                postInfo.getRecordValidFromDate(),
                postInfoDetailResourceList.stream()
                        .map(PostInfoDetailResource::getResource)
                        .collect(Collectors.toList()));

        return postDto;
    }

    private PostInfoDetail retrievePostInfoDetail(final Long postInfoId) {
        return postInfoService.findActivePostInfoDetailByPostInfoId(postInfoId);
    }

    private List<PostInfoDetailResource> retrievePostInfoDetailResourceList(final Long postInfoDetailId) {
        return postInfoService.findPostInfoDetailResourcesByPostInfoDetailId(postInfoDetailId);
    }

    private UserInfo getUserInfo(final Long userInfoId) {
        final UserInfo userInfo = userInfoService.findById(userInfoId);

        if (userInfo == null) {
            throw new RuntimeException("Could not find user matching user id");
        }

        return userInfo;
    }
}
