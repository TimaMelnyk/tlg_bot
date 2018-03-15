package com.services;

import com.configs.SpecValues;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Created by yaoun on 02.03.2018.
 */
public class StateChecker {
    private BuyService buyService;
    private SellService sellService;
    private SendMessageService sendMessageService;
    public static int currState;

    public int stateChecker(Message message) {
        int state;
        if (message.getText().equals("Купить")) {
            currState = SpecValues.BUYAD;
            return currState;
        }
        if (message.getText().equals("Продать")) {
            currState = SpecValues.SELLAD;
            return currState ;
        }
        if (message.getText().equals("Назад"))
        {
            if (currState>=2 && currState<6) {
                return SpecValues.WELCOME;
            }
            else {
                return SpecValues.BACK;
            }
        }
        if (message.getText().equals("/start"))
        {
            currState = 1;
            return SpecValues.WELCOME;
        }
        else state = 404;

        return state;
    }

    public SendMessage topicsHandle (Message message, SendMessage sendMessage) {
        sendMessage.setText("topic message");
        return sendMessage;
    }
}
