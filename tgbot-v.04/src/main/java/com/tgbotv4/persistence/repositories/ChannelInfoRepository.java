package com.tgbotv4.persistence.repositories;

import com.tgbotv4.persistence.entities.ChannelInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yaoun on 19.03.2018.
 */

public interface ChannelInfoRepository extends JpaRepository<ChannelInfo, Integer> {
    List<ChannelInfo> findByChannelCategory(Integer category);

}
