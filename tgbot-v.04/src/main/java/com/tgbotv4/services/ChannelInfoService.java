package com.tgbotv4.services;

import com.tgbotv4.handlers.QueryController;
import com.tgbotv4.persistence.entities.ChannelInfo;
import com.tgbotv4.persistence.repositories.ChannelInfoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by yaoun on 09.04.2018.
 */
@Service
public class ChannelInfoService {
    @Autowired
    ChannelInfoRepository channelInfoRepository;
    private static final Logger logger = LogManager.getLogger(QueryController.class);
    public Page<ChannelInfo> getChannelInfo (Pageable pageable) {
        return channelInfoRepository.findAll(pageable);
    }

    public Page<ChannelInfo> getChannelInfoByCategory (Integer category, Pageable pageable) {
        return channelInfoRepository.findByChannelCategory(category, pageable);
    }
    public ChannelInfo getChannelInfoById(Integer id) {
        return channelInfoRepository.findById(id).get();
    }

    public void setChannelInfo (ChannelInfo channelInfo) {
        channelInfoRepository.save(channelInfo);
    }

}
