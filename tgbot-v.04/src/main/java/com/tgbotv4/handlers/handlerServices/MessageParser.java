package com.tgbotv4.handlers.handlerServices;

import com.tgbotv4.conf.BotTextMessage;
import com.tgbotv4.conf.MenuBut;
import com.tgbotv4.persistence.repositories.CategoriesRepository;
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

    public int currState;

    public int commandParser(Message message) {
        int state;
        if (message.getText().equals(BotTextMessage.BUYAD)) {
            currState = MenuBut.BUYAD;
            return currState;
        }
        if (message.getText().equals(BotTextMessage.SELLAD)) {
            currState = MenuBut.SELLAD;
            return currState ;
        }
        if (message.getText().equals(BotTextMessage.BUYAD_FILTER_CATEGORY)) {
            currState = MenuBut.BUYAD_FILTER_CATEGORY;
            return currState ;
        }
        if (message.getText().equals(BotTextMessage.BUYAD_FILTER_LOCATION)) {
            currState = MenuBut.BUYAD_FILTER_LOCATION;
            return currState ;
        }
        if (message.getText().equals(BotTextMessage.BUYAD_FILTER_QUANTITY)) {
            currState = MenuBut.BUYAD_FILTER_QUANTITY;
            return currState ;
        }
        if (message.getText().equals(BotTextMessage.BUYAD_FILTER_TOP)) {
            currState = MenuBut.BUYAD_FILTER_TOP;
            return currState ;
        }
        if (message.getText().equals(BotTextMessage.BACK))
        {
            if (currState>=2 && currState<6) {
                return MenuBut.WELCOME;
            }
            else {
                return MenuBut.BACK;
            }
        }
        if (message.getText().equals("/start"))
        {
            currState = 1;
            return MenuBut.WELCOME;
        }
        else state = 404;

        return state;
    }
}
