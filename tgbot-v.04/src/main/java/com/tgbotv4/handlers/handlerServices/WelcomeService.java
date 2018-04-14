package com.tgbotv4.handlers.handlerServices;

import com.tgbotv4.conf.BotTextMessage;
import com.tgbotv4.services.ChannelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by yaoun on 25.03.2018.
 */

@Service
public class WelcomeService {

    @Autowired
    ChannelInfoService channelInfoService;

    public SendMessage welcomePage (Message message, SendMessage sendMessage) {
        sendMessage
                .setChatId(message.getChatId())
                .setText(BotTextMessage.GREETING_TEXT + "\n" + BotTextMessage.BUY_SELL_TEXT);
        return sendMessage;
    }
}

