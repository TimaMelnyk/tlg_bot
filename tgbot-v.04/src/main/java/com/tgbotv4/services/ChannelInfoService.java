package com.tgbotv4.services;

import com.tgbotv4.persistence.entities.ChannelInfo;
import com.tgbotv4.persistence.repositories.ChannelInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yaoun on 09.04.2018.
 */
@Service
public class ChannelInfoService {
    @Autowired
    ChannelInfoRepository channelInfoRepository;

    public List<ChannelInfo> getChannelInfo () {
        return channelInfoRepository.findAll();
    }

    public List<ChannelInfo> getChannelInfoByCategory (Integer category) {
        return channelInfoRepository.findByChannelCategory(category);
    }
    public ChannelInfo getChannelInfoById(Integer id) {
        return channelInfoRepository.findById(id).get();
    }

}
