package com.services;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by yaoun on 02.03.2018.
 */
public class BuyService {
    public SendMessage buydHandle (Message message, SendMessage sendMessage) {
        sendMessage.setText("buy");
        return sendMessage;
    }
}
