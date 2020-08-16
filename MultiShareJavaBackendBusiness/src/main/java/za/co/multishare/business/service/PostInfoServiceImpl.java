package za.co.multishare.business.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import za.co.multishare.domain.entity.PostInfo;
import za.co.multishare.domain.entity.PostInfoDetail;
import za.co.multishare.domain.entity.PostInfoDetailResource;
import za.co.multishare.domain.entity.UserInfo;
import za.co.multishare.repository.repository.PostInfoDetailResourceRepository;
import za.co.multishare.repository.repository.PostInfoDetailsRepository;
import za.co.multishare.repository.repository.PostInfoRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostInfoServiceImpl implements PostInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostInfoServiceImpl.class);

    private final PostInfoRepository postInfoRepository;
    private final PostInfoDetailsRepository postInfoDetailsRepository;
    private final PostInfoDetailResourceRepository postInfoDetailResourceRepository;

    @Autowired
    public PostInfoServiceImpl(final PostInfoRepository postInfoRepository,
                               final PostInfoDetailsRepository postInfoDetailsRepository,
                               final PostInfoDetailResourceRepository postInfoDetailResourceRepository) {
        this.postInfoRepository = postInfoRepository;
        this.postInfoDetailsRepository = postInfoDetailsRepository;
        this.postInfoDetailResourceRepository = postInfoDetailResourceRepository;
    }

    @Transactional
    @Override
    public PostInfo createPostInfo(UserInfo userInfo,
                                   String postTitle,
                                   String postBody,
                                   List<String> resourcesList,
                                   LocalDateTime recordValidFromDate,
                                   LocalDateTime recordValidToDate) {

        PostInfo postInfo = new PostInfo(null, recordValidFromDate,
                recordValidToDate, userInfo);

        postInfo = postInfoRepository.save(postInfo);

        final PostInfoDetail postInfoDetail = createPostInfoDetail(postInfo, postTitle,
                postBody, recordValidFromDate, recordValidToDate);

        if (!resourcesList.isEmpty()) {
            createPostInfoDetailResource(postInfoDetail, resourcesList, recordValidFromDate, recordValidToDate);
        }

        return postInfo;
    }

    @Override
    public PostInfoDetail createPostInfoDetail(final PostInfo postInfo,
                                               final String title,
                                               final String postBody,
                                               final LocalDateTime recordValidFromDate,
                                               final LocalDateTime recordValidToDate) {

        final PostInfoDetail postInfoDetail = new PostInfoDetail(null, title,
                postBody, recordValidFromDate, recordValidToDate, postInfo);

        return postInfoDetailsRepository.save(postInfoDetail);
    }

    @Override
    public List<PostInfoDetailResource> createPostInfoDetailResource(final PostInfoDetail postInfoDetail,
                                                                     final List<String> resourcesList,
                                                                     final LocalDateTime recordValidFromDate,
                                                                     final LocalDateTime recordValidToDate) {
        final List<PostInfoDetailResource> postInfoDetailResourceList = new ArrayList<>();

        resourcesList.forEach(postInfoDetailResource -> {
            postInfoDetailResourceList.add(new PostInfoDetailResource(null, postInfoDetailResource,
                    recordValidFromDate, recordValidToDate, postInfoDetail));
        });

        return postInfoDetailResourceRepository.saveAll(postInfoDetailResourceList);
    }

    @Override
    public Page<PostInfo> findAllUserPostInfo(final Long userId,
                                              final Integer pageNumber,
                                              final Integer pageSize) {

        //todo implement pagination
        final Pageable page = PageRequest.of(pageNumber, pageSize);

        final Page<PostInfo> postInfoPage = postInfoRepository
                .findByUserInfoUserInfoIdAndRecordValidToDateIsNull(userId, page);

        LOGGER.info("\n postInfoList is empty: " + postInfoPage.isEmpty() + " \n");
        LOGGER.info("\n postInfoList: \n" +  postInfoPage.toString() + " \n");

        return postInfoPage;
    }

    @Override
    public List<PostInfo> findAllUserPostInfo(Long userInfoId) {
        return postInfoRepository.findByUserInfoUserInfoIdAndRecordValidToDateIsNull(userInfoId);
    }

    @Override
    public PostInfoDetail findActivePostInfoDetailByPostInfoId(Long postInfoDetailId) {
        return postInfoDetailsRepository
                .findByPostInfoPostInfoIdAndRecordValidToDateIsNull(postInfoDetailId);
    }

    @Override
    public List<PostInfoDetailResource> findPostInfoDetailResourcesByPostInfoDetailId(Long postInfoDetailId) {

        return postInfoDetailResourceRepository
                .findByPostInfoDetailPostInfoDetailIdAndRecordValidToDateIsNull(postInfoDetailId);
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        postInfoDetailResourceRepository.deleteByPostInfoDetailPostInfoPostInfoId(postId);
        postInfoDetailsRepository.deleteByPostInfoPostInfoId(postId);
        postInfoRepository.deleteById(postId);
    }
}
