package za.co.multishare.web.service;

import za.co.multishare.web.domain.dto.FeedDto;

import java.util.List;

public interface FeedService {

    List<FeedDto> getFeeds(Long userInfoId,
                           Integer pageNumber,
                           Integer pageSize);
}
