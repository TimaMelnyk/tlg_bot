package com.controllers;

import com.configs.SpecValues;
import com.services.*;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoun on 02.03.2018.
 */
public class StartController extends TelegramLongPollingBot {
    private StateChecker stateCh = new StateChecker();
    private BuyService buyService = new BuyService();
    private SellService sellService = new SellService();
    private SendMessageService sendMessageService = new SendMessageService();
    private WelcomeService welcomeService = new WelcomeService();

    public String getBotUsername() {return SpecValues.CHANNEL_NAME;}

    public String getBotToken() {
        return SpecValues.KEY;
    }

    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                Message message = update.getMessage();

                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                keyboardMarkup.setResizeKeyboard(true);
                if (message.hasText() || message.hasLocation()) {
                    handleIncomingMessage(message, keyboardMarkup);
                }
            }
        } catch (Exception e) {
            BotLogger.error(SpecValues.LOGTAG, e);
        }
    }

    public void handleIncomingMessage(Message message, ReplyKeyboardMarkup keyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();
        sendMessage.setChatId(message.getChatId().toString());
        int state = stateCh.stateChecker(message);

        switch(state) {
            case SpecValues.WELCOME:
                sendMessage = welcomeService.welcomePage(message, keyboardMarkup);
                break;
            case SpecValues.BUYAD:
                sendMessage = buyService.buydHandle(message, sendMessage);

                row.add("Тема 1");
                row.add("Тема 2");
                row.add("Тема 3");
                row.add("Назад");
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                sendMessage.setReplyMarkup(keyboardMarkup);

                break;
            case SpecValues.SELLAD:
                sendMessage = sellService.sellHandle(message, sendMessage);
                row.add("Отправить");
                row.add("Назад");
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                sendMessage.setReplyMarkup(keyboardMarkup);
                break;
            case SpecValues.TOPICS:
                sendMessage = stateCh.topicsHandle(message, sendMessage);
                break;
            case SpecValues.BACK:
                sendMessage.setText("back");
                break;
            default:
                sendMessage = sendMessageService.sendMessageDefault(message, sendMessage);
                break;
        }



       /* InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Перейти на канал").setUrl("https://t.me/uxidesign"));

        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(markupInline);

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);*/


        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
