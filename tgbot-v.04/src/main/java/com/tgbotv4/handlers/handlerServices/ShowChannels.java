package com.tgbotv4.handlers.handlerServices;

import com.tgbotv4.persistence.entities.ChannelInfo;
import com.tgbotv4.services.ChannelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;

import java.util.List;

/**
 * Created by yaoun on 10.04.2018.
 */
@Service
public class ShowChannels {
    @Autowired
    ChannelInfoService channelInfoService;


    public String showChannelsByCategory(int categoryId){
        String answer = "";
        int counter = 1;
        List<ChannelInfo> channelInfos = channelInfoService.getChannelInfoByCategory(categoryId);
        for(ChannelInfo i : channelInfos) {
            answer+= counter + ". "
                    + i.getChannelUrl() + "\n"
                    + i.getChannelDescriptione() + "\n"
                    + "/showInfo" + i.getId() + "\n\n";
            counter++;
        }
        return answer;
    }

    public SendMessage showChannel(SendMessage sendMessage, int id) {
        String answer = "";
        ChannelInfo channelInfo = channelInfoService.getChannelInfoById(id);
            answer = "<i>" + channelInfo.getChannelName() + "</i>\n"
                    + "<b>Описание : </b>\n"
                    + channelInfo.getChannelDescriptione() + "\n"
                    + channelInfo.getChannelUrl() + "\n";
            sendMessage.setText(answer)
                    .enableHtml(true);
        return sendMessage;
    }
}
