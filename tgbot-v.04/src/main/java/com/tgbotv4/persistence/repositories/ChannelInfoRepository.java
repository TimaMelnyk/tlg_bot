package com.tgbotv4.persistence.repositories;

import com.tgbotv4.persistence.entities.ChannelInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by yaoun on 19.03.2018.
 */

public interface ChannelInfoRepository extends PagingAndSortingRepository<ChannelInfo, Integer> {
    Page<ChannelInfo> findByChannelCategory(Integer category, Pageable pageable);
    Page<ChannelInfo> findByChannelUrlContaining(String str, Pageable pageable);

}
