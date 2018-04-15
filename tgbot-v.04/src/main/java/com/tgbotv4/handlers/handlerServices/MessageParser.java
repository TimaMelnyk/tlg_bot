package com.tgbotv4.handlers.handlerServices;

import com.tgbotv4.conf.BotTextMessage;
import com.tgbotv4.conf.MenuBut;
import com.tgbotv4.persistence.repositories.CategoriesRepository;
import com.tgbotv4.services.ChannelInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by yaoun on 26.03.2018.
 */

@Service
public class MessageParser {
    @Autowired
    CategoriesRepository categoriesRepository;
    @Autowired
    ChannelInfoService channelInfoService;
    public int currState;

    private static final Logger logger = LogManager.getLogger(MessageParser.class);

    public int commandParser(Message message) {
        int state;
        String str = message.getText();
        if (message.getText().equals(BotTextMessage.BUYAD)) {
            currState = MenuBut.BUYAD;
            return currState;
        }
        if (message.getText().equals(BotTextMessage.SELLAD)) {
            currState = MenuBut.SELLAD;
            return currState;
        }
        if (message.getText().equals(BotTextMessage.BUYAD_FILTER_CATEGORY)) {
            currState = MenuBut.BUYAD_FILTER_CATEGORY;
            return currState;
        }
        if (message.getText().equals(BotTextMessage.BUYAD_FILTER_LOCATION)) {
            currState = MenuBut.BUYAD_FILTER_LOCATION;
            return currState;
        }
        if (message.getText().equals(BotTextMessage.BUYAD_FILTER_QUANTITY)) {
            currState = MenuBut.BUYAD_FILTER_QUANTITY;
            return currState;
        }
        if (message.getText().startsWith("@")) {
            currState = MenuBut.BUYAD_FILTER_SEARCH;
            return currState;
        }
        if (message.getText().equals(BotTextMessage.BUYAD_FILTER_SEARCH)) {
            currState = MenuBut.BUYAD_FILTER_SEARCH;
            return currState;
        }

        if (message.getText().equals(BotTextMessage.BACK)) {
            if (currState >= 2 && currState < 6) {
                return MenuBut.WELCOME;
            } else {
                return MenuBut.BACK;
            }
        }
        if (message.getText().toLowerCase().contains("/showInfo".toLowerCase())) {
            str = str.substring(str.length() - 2);
            System.out.println("channelInfo" + str);
            return MenuBut.SHOW_CHANNEL_INFO;
        }
        if (message.getText().equals("/start")) {
            currState = 1;
            return MenuBut.WELCOME;
        } else state = 404;
        logger.info(state);
        return state;
    }

    public int getChannelIdToShow(Message message){
        String str = message.getText();
        return Integer.parseInt(str.replaceAll("\\D+",""));
    }

    public int getCategoryId(String message){
        if(message.toLowerCase().contains("page")) {
            return Integer.parseInt(message.split("[.]")[0].replaceAll("\\D+",""));
        }
        return Integer.parseInt(message.replaceAll("\\D+",""));
    }

    public String getSearchStr(String message){
        if(message.toLowerCase().contains("search")) {
            return message.substring(message.indexOf("@")+1, message.indexOf("."));
        }else
        return "nothing";
    }

    public int getPageId(String message){
        if(!message.toLowerCase().contains("page")) {
            return 0;
        }else
        return Integer.parseInt(message.split("[.]")[1].replaceAll("\\D+",""));
    }

    public int queryParser(String message){
        if(message.toLowerCase().contains("category")) {
            return MenuBut.BUYAD_FILTER_CATEGORY;
        }
        if(message.toLowerCase().contains("search")) {
            return MenuBut.BUYAD_FILTER_SEARCH;
        }
        return 404;
    }

    public boolean validateSearchQuery (String message) {
        if (message.split("[@]")[1].length() >= 1 && message.length() <= 32) {
            return true;
        }
        else
            return false;
    }
}
