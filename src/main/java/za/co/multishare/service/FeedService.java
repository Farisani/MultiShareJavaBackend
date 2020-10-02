package za.co.multishare.service;

import za.co.multishare.domain.dto.FeedDto;

import java.util.List;

public interface FeedService {

    List<FeedDto> getFeeds(Long userInfoId,
                           Integer pageNumber,
                           Integer pageSize);
}
