package com.tgbotv4.handlers;


import com.tgbotv4.conf.BotTextMessage;
import com.tgbotv4.conf.MenuBut;
import com.tgbotv4.handlers.handlerServices.BuyService;
import com.tgbotv4.handlers.handlerServices.MessageParser;
import com.tgbotv4.handlers.handlerServices.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoun on 26.03.2018.
 */
@Controller
public class MessageController {
    @Autowired
    MessageParser messageParser;
    @Autowired
    WelcomeService welcomeService;
    @Autowired
    BuyService buyService;

    public SendMessage handleIncomingMessage(Message message, ReplyKeyboardMarkup keyboardMarkup) throws TelegramApiException {

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableMarkdown(true);
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        sendMessage.setChatId(message.getChatId().toString());
        int state = messageParser.commandParser(message);

        switch (state) {
            case MenuBut.WELCOME:
                row.add(BotTextMessage.BUYAD);
                row.add(BotTextMessage.SELLAD);
                keyboard.add(row);
                keyboardMarkup.setOneTimeKeyboard(true);
                keyboardMarkup.setKeyboard(keyboard);
                sendMessage.setReplyMarkup(keyboardMarkup);

                return welcomeService.welcomePage(message, sendMessage);
            case MenuBut.BUYAD:

                row.add(BotTextMessage.BUYAD_FILTER_CATEGORY);
                row.add(BotTextMessage.BUYAD_FILTER_LOCATION);
                row2.add(BotTextMessage.BUYAD_FILTER_QUANTITY);
                row2.add(BotTextMessage.BUYAD_FILTER_TOP);
                row3.add(BotTextMessage.BACK);

                keyboard.add(row);
                keyboard.add(row2);
                keyboard.add(row3);
                keyboardMarkup.setOneTimeKeyboard(true);
                keyboardMarkup.setKeyboard(keyboard);
                keyboardMarkup.setResizeKeyboard(true);
                sendMessage.setReplyMarkup(keyboardMarkup);
                sendMessage = buyService.chooseFilter(sendMessage, keyboard);

                return sendMessage;
            case MenuBut.BACK:
                sendMessage.setText("back");
                break;
            case MenuBut.BUYAD_FILTER_CATEGORY:
                sendMessage.setText( "Категории : \n");
                return buyService.categoriesList(sendMessage);
            case MenuBut.BUYAD_FILTER_LOCATION:
                sendMessage.setText("here i get all locations from db and put one by one");
//                return buyService.locationList();
                break;
            case MenuBut.BUYAD_FILTER_QUANTITY:
                sendMessage.setText("here i let user put two number or give him basic (>1,000, <10,000)");
//                return buyService.getquantity();
                break;
            case MenuBut.BUYAD_FILTER_TOP:
                sendMessage.setText("here i get all top channels from db and put one by one");
//                return buyService.topChannelList();
                break;
            default:
                System.out.println("hello");
                return sendMessage;
        }
        return sendMessage;
    }
}
