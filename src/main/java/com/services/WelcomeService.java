package com.services;

import com.configs.SpecValues;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoun on 02.03.2018.
 */
public class WelcomeService {
    public SendMessage welcomePage (Message message, ReplyKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage() // Create a message object object
                .setChatId(message.getChatId())
                .setText(SpecValues.GREETING_TEXT + "\n" + SpecValues.BUY_SELL_TEXT);

        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();
        row.add("Купить");
        row.add("Продать");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }
}
