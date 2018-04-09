package com.tgbotv4;


import com.tgbotv4.conf.BotConfigurations;
import com.tgbotv4.handlers.MessageController;
import com.tgbotv4.handlers.QueryController;
import com.tgbotv4.handlers.handlerServices.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import javax.annotation.PostConstruct;


/**
 * Created by yaoun on 25.03.2018.
 */
@Component
public class BotInitializer extends TelegramLongPollingBot {

    @Autowired
    MessageController messageController;
    @Autowired
    QueryController queryController;
    @Autowired
    BuyService buyService;

    static {
        ApiContextInitializer.init();
    }

    public String getBotUsername() {return BotConfigurations.CHANNEL_NAME;}

    public String getBotToken() {
        return BotConfigurations.KEY;
    }

    @PostConstruct
    public void registerBot() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            System.out.println(e);
        }
    }

    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message message = update.getMessage();

                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                keyboardMarkup.setResizeKeyboard(true);
                if (message.hasText() || message.hasLocation()) {
                    SendMessage sendMessage;
                    sendMessage = messageController.handleIncomingMessage(message,keyboardMarkup);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (update.hasCallbackQuery()){
                EditMessageText editMessageText = new EditMessageText();
                    editMessageText = queryController.handleIncomingMessage(update,editMessageText);
                    try {
                        execute(editMessageText);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
            }
        } catch (Exception e) {
            BotLogger.error("CommandHandler", e);
        }
    }
}
